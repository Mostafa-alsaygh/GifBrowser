package com.example.gifbrowserapp.data.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject


interface NetworkMonitor {
    val isConnected: StateFlow<Boolean>
    fun unregisterNetworkCallback()
}

class NetworkMonitorImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkMonitor {

    private val _networkState = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> = _networkState.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkState.value = true
        }

        override fun onLost(network: Network) {
            _networkState.value = false
        }
    }

    init {
        _networkState.value = isNetworkCurrentlyAvailable()
        registerNetworkCallback()
    }

    private fun isNetworkCurrentlyAvailable(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    private fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun unregisterNetworkCallback() {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            Timber.e(e, "Failed to unregister network callback")
        }
    }
}
