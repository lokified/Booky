package com.loki.booko.presentation.search

import com.loki.booko.domain.models.Term

data class SearchState(
    val errorMessage: String = "",
    val searchTermList: List<Term> = emptyList()
)
