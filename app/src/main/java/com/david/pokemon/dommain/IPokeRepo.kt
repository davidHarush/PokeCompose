package com.david.pokemon.dommain

import kotlinx.coroutines.flow.Flow

interface IPokeRepo {

    suspend fun getPokemonList(page: Int = 0): Flow<ArrayList<PokeCoreDataCharacter>>
    suspend fun searchPokemon(prefix: String): Flow<ArrayList<PokeCoreDataCharacter>>
    suspend fun getPokemon(pokeCoreDataCharacter: PokeCoreDataCharacter): Flow<PokeCharacter>

}