package com.loki.booko.data.remote.googleBookResponse

data class GoogleBookResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)