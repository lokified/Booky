package com.loki.booko.presentation.book_detail

import com.loki.booko.domain.models.Favorite

data class BookDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val favorite: Favorite? = null,
    val bookSynopsis: String? = null
)
