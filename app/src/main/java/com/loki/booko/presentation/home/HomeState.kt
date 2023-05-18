package com.loki.booko.presentation.home

import com.loki.booko.data.remote.response.BookDto

data class HomeState(
    val isLoading: Boolean = false,
    var bookList: List<BookDto> = emptyList(),
    val errorMessage: String = ""
)
