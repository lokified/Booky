package com.loki.booko.presentation.settings

import androidx.lifecycle.viewModelScope
import com.loki.booko.data.local.datastore.DataStoreStorage
import com.loki.booko.presentation.MainViewModel
import com.loki.booko.util.DownloadMedium
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreStorage
): MainViewModel(dataStore) {

    fun updateDownloadLocation(location: String) {
        viewModelScope.launch {
            dataStore.changeLocation(location)
        }
    }

    fun changeDownloadMedium(medium: DownloadMedium) {
        viewModelScope.launch {
            dataStore.setDownloadMedium(medium.toPreferenceString())
        }
    }

}