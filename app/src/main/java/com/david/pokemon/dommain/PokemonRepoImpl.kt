package com.david.pokemon.dommain

import com.david.pokemon.data.IPokeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PokemonRepoImpl @Inject constructor(
    private val api: IPokeApiService,
) : IPokeRepo {

    override suspend fun getPokemonList(page: Int): Flow<ArrayList<PokeCoreDataCharacter>> = flow {
        val offset = page * PAGE_SIZE
        val result = api.getPokemonList(offset = offset, limit = PAGE_SIZE)
        emit(Mapper.convertPokemonList(result.results))
    }

    override suspend fun searchPokemon(prefix: String): Flow<ArrayList<PokeCoreDataCharacter>> {
        return flow {
            emptyFlow<PokeCoreDataCharacter>()
        }
    }

    override suspend fun getPokemon(pokeCoreDataCharacter: PokeCoreDataCharacter): Flow<PokeCharacter> =
        flow {
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