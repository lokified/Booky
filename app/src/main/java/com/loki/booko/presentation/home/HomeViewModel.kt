package com.loki.booko.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.domain.models.Term
import com.loki.booko.domain.repository.local.BookRepository
import com.loki.booko.domain.repository.local.SearchTermRepository
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
): ViewModel() {

    private val _bookState = MutableStateFlow(HomeState())
    val bookState = _bookState.asStateFlow()

    init {
        viewModelScope.launch {
            getBooks()
        }
    }


    private suspend fun getBooks() {

        booksRepository.getBooks().onEach { result ->

            when(result) {

                is Resource.Loading -> {
                    _bookState.value = HomeState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _bookState.value = HomeState(
                        bookList = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _bookState.value = HomeState(
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun searchBook(term: String) {

        booksRepository.searchBook(term).onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _bookState.value = HomeState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _bookState.value = HomeState(
                        bookList = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _bookState.value = HomeState(
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}