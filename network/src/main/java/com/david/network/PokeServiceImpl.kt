package com.david.network

import android.util.Log
import com.david.network.dto.Pokemon
import com.david.network.dto.PokemonList
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json


class PokeServiceImpl(
    private val client: HttpClient,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }
) : IPokeService {


    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonList? {
        return try {
            client.get {
                url(HttpRoutes.POKEMON)
                parameter(HttpRoutes.limit, limit)
                parameter(HttpRoutes.offset, offset)
            }
        } catch (e: Exception) {
            handleException(e)
            null
        }

    }

    override suspend fun getPokemonInfo(name: String): Pokemon {
        return try {

            val pokeResult : String =  client.get {
                url(HttpRoutes.POKEMON + "/"+name)
            }

           json.decodeFromString(string = pokeResult, deserializer =  Pokemon.serializer())

        } catch (e: Exception) {
            handleException(e)
            Pokemon(experience = 0, height = 0, id = 0,
                name = "", order = 0,
                stats = emptyList(), weight = 0)
        }
    }


    private fun handleException(e: Exception) {
        when (e) {
            // 3xx - responses
            is RedirectResponseException -> Log.w(MODEL_TAG, "Redirect", e)
            // 4xx - responses
            is ClientRequestException -> Log.w(MODEL_TAG, "Client Error", e)
            // 5xx - responses
            is ServerResponseException -> Log.w(MODEL_TAG, "Server Error", e)
            else -> Log.w(MODEL_TAG, "Other Error", e)
        }
    }

}