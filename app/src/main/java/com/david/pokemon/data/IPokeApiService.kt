package com.david.pokemon.data

import com.david.pokemon.data.module.Pokemon
import com.david.pokemon.data.module.PokemonList

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPokeApiService {
    companion object {
        const val BASE_URL: String = "https://pokeapi.co/api/v2/"
    }

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Pokemon
}