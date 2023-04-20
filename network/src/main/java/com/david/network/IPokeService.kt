package com.david.network

import com.david.network.dto.Pokemon
import com.david.network.dto.PokemonList
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.ContentType.Application.Json

interface IPokeService {

    suspend fun getPokemonList(
       offset: Int,
       limit: Int,
    ): PokemonList?

    suspend fun getPokemonInfo(name: String): Pokemon?

    companion object {
        fun create(): IPokeService {

            return PokeServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}