package com.loki.booko.presentation.book_detail

import com.loki.booko.domain.models.BookDto

data class BookDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val book: BookDto? = null,
    val bookSynopsis: String? = null
)
