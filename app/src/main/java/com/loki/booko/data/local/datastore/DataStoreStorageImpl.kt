package com.loki.booko.data.local.datastore

import android.os.Environment
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.loki.booko.data.local.datastore.DataStoreStorage.PreferenceKey.LOCATION_KEY
import com.loki.booko.data.local.datastore.DataStoreStorage.PreferenceKey.MEDIUM_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreStorage {

    override suspend fun changeLocation(location: String) {
        dataStore.edit { preference ->
            preference[LOCATION_KEY] = location
        }
    }

    override suspend fun getLocation(): Flow<String> = dataStore
        .data.map { it[LOCATION_KEY] ?: Environment.DIRECTORY_DOWNLOADS }

    override suspend fun setDownloadMedium(medium: String) {
        dataStore.edit { preference ->
            preference[MEDIUM_KEY] = medium
        }
    }

    override suspend fun getDownloadMedium(): Flow<String> = dataStore
        .data.map { it[MEDIUM_KEY] ?: "Mobile data and Wifi" }
}