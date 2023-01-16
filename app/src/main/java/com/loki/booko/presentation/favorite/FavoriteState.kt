package com.loki.booko.presentation.favorite

import com.loki.booko.domain.models.BookDto

data class FavoriteState(
    val isLoading: Boolean = false,
    val favoriteList: List<BookDto> = emptyList()
)
