package com.loki.booko.data.remote.response

data class BookResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<BookDto>
)