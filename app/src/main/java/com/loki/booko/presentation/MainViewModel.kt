package com.loki.booko.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.data.local.datastore.DataStoreStorage
import com.loki.booko.util.DownloadMedium
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


open class MainViewModel (
    private val dataStoreStorage: DataStoreStorage
): ViewModel() {


    val downloadLocation = mutableStateOf("")
    val downloadMedium = mutableStateOf("")
    val downloadMessage = mutableStateOf("")

    init {
        getDownloadLocation()
        getDownloadMedium()
    }

    private fun getDownloadLocation() {

        viewModelScope.launch {
            dataStoreStorage.getLocation().collect { location ->
                downloadLocation.value = location
            }
        }
    }

    private fun getDownloadMedium() {
        viewModelScope.launch {
            dataStoreStorage.getDownloadMedium().collect { medium ->
                downloadMedium.value = medium
            }
        }
    }

    fun getMedium(): DownloadMedium {
        return DownloadMedium.values()
            .find {
                it.toPreferenceString() == downloadMedium.value
            } ?: DownloadMedium.BOTH
    }
}