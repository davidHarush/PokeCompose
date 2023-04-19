package com.david.pokemon.dommain

import com.david.network.IPokeService
import com.david.network.dto.Result
import com.david.network.dto.getId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PokemonRepo @Inject constructor(
) {

    private val service = IPokeService.create()

    suspend fun getPokemonList(offset: Int = 0): Flow<ArrayList<PokeCoreDataCharacter>> = flow {
        val re = service.getPokemonList(offset = offset, limit = 20)
        emit(convertPokemonList(re?.results))
    }

    private suspend fun convertPokemonList(results: List<Result>?): ArrayList<PokeCoreDataCharacter> {
        val list = arrayListOf<PokeCoreDataCharacter>()
        results?.forEachIndexed { _, character ->
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
        val pokemon = service.getPokemonInfo(pokeCoreDataCharacter.id)
        if(pokemon == null){
            emit(PokeCharacter.getEmpty())
        }

        val stat = arrayListOf<Stat>()
        pokemon!!.stats.forEach { stats ->
            stat.add(Stat(power = stats.base_stat, name = stats.stat.name))
        }
        val character = PokeCharacter(
            coreData = pokeCoreDataCharacter,
            stats = stat,
            pokemon.height,
            pokemon.weight,
            pokemon.experience
        )
        emit(character)
    }


}