package com.loki.booko.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.data.local.datastore.DataStoreStorage
import com.loki.booko.data.local.datastore.DataStoreStorageImpl
import com.loki.booko.util.DownloadMedium
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreStorage
): ViewModel() {

    val downloadLocation = mutableStateOf("")
    val downloadMedium = mutableStateOf("")

    init {
        getDownloadLocation()
        getDownloadMedium()
    }

    fun updateDownloadLocation(location: String) {

        viewModelScope.launch {
            dataStore.changeLocation(location)
        }
    }

    private fun getDownloadLocation() {

        viewModelScope.launch {
            dataStore.getLocation().collect { location ->
                downloadLocation.value = location
            }
        }
    }


    fun changeDownloadMedium(medium: DownloadMedium) {
        viewModelScope.launch {
            dataStore.setDownloadMedium(medium.toPreferenceString())
        }
    }

    private fun getDownloadMedium() {
        viewModelScope.launch {
            dataStore.getDownloadMedium().collect { medium ->
                downloadMedium.value = medium
            }
        }
    }
}