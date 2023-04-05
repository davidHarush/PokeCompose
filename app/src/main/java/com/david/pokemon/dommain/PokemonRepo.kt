package com.david.pokemon.dommain

import com.david.pokemon.data.IPokeApiService
import com.david.pokemon.data.module.Result
import com.david.pokemon.data.module.getId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PokemonRepo @Inject constructor(
    private val api: IPokeApiService,
) {

    suspend fun getPokemonList(offset: Int = 0): Flow<ArrayList<PokeCoreDataCharacter>> = flow {
        val re = api.getPokemonList(offset = offset, limit = 20)
        emit(convertPokemonList(re.results))
    }

    private suspend fun convertPokemonList(results: List<Result>): ArrayList<PokeCoreDataCharacter> {
        val list = arrayListOf<PokeCoreDataCharacter>()
        results.forEachIndexed { _, character ->
            list.add(
                PokeCoreDataCharacter(
                    id = character.getId(),
                    name = character.name.capitalize()
                )
            )
        }

        return list
    }

    suspend fun getPokemon(pokeCoreDataCharacter: PokeCoreDataCharacter) = flow {
        val pokemon = api.getPokemonInfo(pokeCoreDataCharacter.id.toString())
        val stat = arrayListOf<Stat>()
        pokemon.stats.forEach { stats ->
            stat.add(Stat(power = stats.baseStat, name = stats.stat.name))
        }
        val character = PokeCharacter(
            coreData = pokeCoreDataCharacter,
            stats = stat,
            pokemon.height,
            pokemon.weight,
            pokemon.baseExperience
        )
        emit(character)
    }


}