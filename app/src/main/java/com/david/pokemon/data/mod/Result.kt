package com.david.pokemon.data.mod

import androidx.core.text.isDigitsOnly

data class Result(
    val name: String,
    val url: String
)

fun Result.getId() = url.split('/').first {
    it.isDigitsOnly() && it.trim().isNotEmpty()
}