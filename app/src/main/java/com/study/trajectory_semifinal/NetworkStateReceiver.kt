package com.study.trajectory_semifinal

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import com.study.trajectory_semifinal.fragments.Subscriber

class NetworkStateReceiver(context: Context?, subscriber: Subscriber){
    init {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                subscriber.notifyStateChanged(true)
            }

            override fun onLost(network: Network) {
                subscriber.notifyStateChanged(false)
            }
        })
    }

}