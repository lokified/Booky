package com.loki.booko.presentation.search

import com.loki.booko.domain.models.BookItem
import com.loki.booko.domain.models.Term

data class SearchState(
    val errorMessage: String = "",
    val searchTermList: List<Term> = emptyList(),
    val isLoading: Boolean = false,
    var bookList: List<BookItem> = emptyList()
)
