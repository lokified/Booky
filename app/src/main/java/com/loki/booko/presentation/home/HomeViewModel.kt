package com.loki.booko.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookUseCase: BookUseCase
): ViewModel() {

    private val _bookState = mutableStateOf(HomeState())
    val bookState: State<HomeState> = _bookState

    init {
        getBooks()
    }


    private fun getBooks() {

        bookUseCase.getBookList().onEach { result ->

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
}