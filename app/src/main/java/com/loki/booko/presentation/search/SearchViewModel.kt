package com.loki.booko.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.data.local.mappers.toBookItem
import com.loki.booko.domain.models.Term
import com.loki.booko.domain.repository.local.SearchTermRepository
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTermRepository: SearchTermRepository,
    private val booksRepository: BooksRepository
): ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    val isLoading = mutableStateOf(false)
    val isSearching = mutableStateOf(false)
    val searchTerm = mutableStateOf("")

    init {
        getSearchTermList()
    }


    fun searchBook(term: String) {

        viewModelScope.launch {
            booksRepository.searchBook(term).collect { result ->

                when (result) {

                    is Resource.Loading -> {
                        isLoading.value = true
                        _searchState.value = SearchState(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        isLoading.value = false

                        _searchState.value = SearchState(
                            bookList = result.data?.map { it.toBookItem() } ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        isLoading.value = false

                        _searchState.value = SearchState(
                            errorMessage = result.message ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun initializeScreen() {
        _searchState.update {
            SearchState(
                isLoading = false,
                bookList = emptyList(),
                errorMessage = ""
            )
        }
        getSearchTermList()
    }

    fun saveSearchTerm(term: String) {
        viewModelScope.launch {
            searchTermRepository.saveSearchTerm(
                Term(
                    id = 0,
                    searchTerm = term
                )
            )
        }
    }

    private fun getSearchTermList() {
        viewModelScope.launch {
            searchTermRepository.getAllTerms().collectLatest {
                _searchState.value = SearchState(
                    searchTermList = it.distinct()
                )
            }
        }
    }

    fun deleteSearchTerm(term: Term) {
        viewModelScope.launch {
            searchTermRepository.deleteSearchTerm(term)
        }
    }
}