package com.david.network

import android.util.Log
import com.david.network.dto.Pokemon
import com.david.network.dto.PokemonList
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.cache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.ContentType.Application.Json

interface IPokeService {

    suspend fun getPokemonList(
       offset: Int,
       limit: Int,
    ): PokemonList?

    suspend fun getPokemonPage(@androidx.annotation.IntRange(from = 0, to = MAX_PAGE.toLong()) page: Int
    ): PokemonList?


    suspend fun getAllPokemons(): PokemonList?

    suspend fun getPokemonInfo(name: String): Pokemon?

    companion object {
        fun create(): IPokeService {

            return PokeServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.INFO
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                    install(HttpCache)
                }
            )
        }
    }
}