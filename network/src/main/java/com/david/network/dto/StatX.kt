package com.david.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatX(
    val name: String,
    val url: String,
)