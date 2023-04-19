package com.david.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonList(
    val count: Int?= null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Result>? = null,
)