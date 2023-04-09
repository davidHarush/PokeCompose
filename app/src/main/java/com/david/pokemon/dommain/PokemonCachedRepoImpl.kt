package com.david.pokemon.dommain

import android.util.Log
import com.david.pokemon.data.IPokeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PokemonCachedRepoImpl @Inject constructor(
    private val api: IPokeApiService,
) : IPokeRepo {

    private var pokeList: ArrayList<PokeCoreDataCharacter> = arrayListOf()

    override suspend fun searchPokemon(prefix: String): Flow<ArrayList<PokeCoreDataCharacter>> =
        flow {
            Log.i("dddddyyyy","3-> "+prefix)

            val searchResult = pokeList.filter {
                it.name.lowercase().startsWith(prefix = prefix.lowercase())

            }

            emit(arrayListOf<PokeCoreDataCharacter>().apply {
                searchResult.forEach {
                    Log.i("dddyyy", it.name)
                }
                addAll(searchResult)
            })
        }


    override suspend fun getPokemonList(page: Int): Flow<ArrayList<PokeCoreDataCharacter>> = flow {

        if (pokeList.isEmpty()) {
            Log.i("dddddyyyy","getPokemonList --->>>>>")

            val result = api.getPokemonList(offset = 0, limit = 1100)
            pokeList = Mapper.convertPokemonList(result.results)
        }

        val listPage: ArrayList<PokeCoreDataCharacter> =
            arrayListOf<PokeCoreDataCharacter>().apply {

                val truePage = if (page > LAST_PAGE) {
                    LAST_PAGE
                } else {
                    page
                }

                addAll(
                    pokeList.subList(truePage * PAGE_SIZE, (truePage + 1) * PAGE_SIZE)
                )

            }

        emit(listPage)

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

    fun log() {

        pokeList.forEach {
            Log.i("dddddyyyy",it.id +" : "+ it.name)
        }

    }


}