package com.loki.booko.presentation.favorite

import com.loki.booko.domain.models.BookItem

data class FavoriteState(
    val isLoading: Boolean = false,
    val favoriteList: List<BookItem> = emptyList()
)
