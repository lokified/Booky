package com.loki.booko.presentation.home

import com.loki.booko.domain.models.BookItem

data class HomeState(
    val isLoading: Boolean = false,
    var bookList: List<BookItem> = emptyList(),
    val errorMessage: String = ""
)
