package com.loki.booko.domain.network_service

import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityService {
    val connectivityManager: ConnectivityManager
    val networkStatus: Flow<NetworkStatus>
}