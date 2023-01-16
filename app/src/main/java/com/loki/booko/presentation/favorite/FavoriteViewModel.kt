package com.loki.booko.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.domain.repository.local.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel(){

    private val _favoriteState = MutableStateFlow(FavoriteState())
    val favoriteState = _favoriteState.asStateFlow()

    init {
        getFavoriteBooks()
    }

    private fun getFavoriteBooks() {

        viewModelScope.launch {

            _favoriteState.value = FavoriteState(
                isLoading = true
            )

            delay(1000L)

            bookRepository.getAllBooks().collectLatest {
                _favoriteState.value = FavoriteState(
                    favoriteList = it
                )
            }
        }
    }
}