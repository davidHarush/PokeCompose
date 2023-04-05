package com.david.pokemon.data.module

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>,
)