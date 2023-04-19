package com.david.network.dto
import kotlinx.serialization.Serializable

import androidx.core.text.isDigitsOnly

@Serializable
data class Result(
    val name: String,
    val url: String,
)

fun Result.getId() = url.split('/').first {
    it.isDigitsOnly() && it.trim().isNotEmpty()
}