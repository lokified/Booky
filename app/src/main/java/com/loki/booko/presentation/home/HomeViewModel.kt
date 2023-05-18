package com.loki.booko.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.loki.booko.data.local.mappers.toBookItem
import com.loki.booko.domain.models.BookEntity
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    pager: Pager<Int, BookEntity>

): ViewModel() {

    val bookPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toBookItem() }
        }.cachedIn(viewModelScope)

    private val _bookState = MutableStateFlow(HomeState())
    val bookState = _bookState.asStateFlow()

//    init {
//        viewModelScope.launch {
//            getBooks()
//        }
//    }


//    private suspend fun getBooks() {
//
//        booksRepository.getBooks(9).onEach { result ->
//
//            when(result) {
//
//                is Resource.Loading -> {
//                    _bookState.value = HomeState(
//                        isLoading = true
//                    )
//                }
//
//                is Resource.Success -> {
//                    _bookState.value = HomeState(
//                        bookList = result.data ?: emptyList()
//                    )
//                }
//
//                is Resource.Error -> {
//                    _bookState.value = HomeState(
//                        errorMessage = result.message ?: "Something went wrong"
//                    )
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

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