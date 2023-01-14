package com.loki.booko.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.domain.models.Term
import com.loki.booko.domain.repository.local.SearchTermRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTermRepository: SearchTermRepository
): ViewModel() {

    private val _searchTermState = mutableStateOf(SearchState())
    val searchTermState: State<SearchState> = _searchTermState

    init {
        getSearchTermList()
    }

    private fun getSearchTermList() {
        viewModelScope.launch {
            searchTermRepository.getAllTerms().collectLatest {
                _searchTermState.value = SearchState(
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