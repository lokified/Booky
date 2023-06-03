package com.loki.booko.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

interface DataStoreStorage {

    suspend fun changeLocation(location: String)

    suspend fun getLocation(): Flow<String>

    suspend fun setDownloadMedium(medium: String)

    suspend fun getDownloadMedium(): Flow<String>

    object PreferenceKey {
        val LOCATION_KEY = stringPreferencesKey("location_key")
        val MEDIUM_KEY = stringPreferencesKey("medium_key")
    }
}