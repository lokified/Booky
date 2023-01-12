package com.loki.booko.presentation.home

import com.loki.booko.domain.models.BookDto

data class HomeState(
    val isLoading: Boolean = false,
    val bookList: List<BookDto> = emptyList(),
    val errorMessage: String = ""
)
