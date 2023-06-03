package com.loki.booko.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.data.local.mappers.toBookItem
import com.loki.booko.domain.network_service.NetworkConnectivityService
import com.loki.booko.domain.network_service.NetworkStatus
import com.loki.booko.domain.repository.local.FavoriteBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteBookRepository: FavoriteBookRepository,
    networkConnectivityService: NetworkConnectivityService
): ViewModel(){

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityService.networkStatus.stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

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

            favoriteBookRepository.getAllBooks().collectLatest {
                _favoriteState.value = FavoriteState(
                    favoriteList = it.map { it.toBookItem() }
                )
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            favoriteBookRepository.deleteAll()
        }
    }
}