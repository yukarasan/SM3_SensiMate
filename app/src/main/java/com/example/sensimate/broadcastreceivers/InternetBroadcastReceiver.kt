package com.example.sensimate.broadcastreceivers

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.example.sensimate.data.SaveBoolToLocalStorage

fun InternetBroadcastReceiver(context: Context) {

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetworkInfo = connectivityManager.activeNetworkInfo

    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
        SaveBoolToLocalStorage("hasNet", true, context)
        Log.d("you", "have net zzz")
    } else {
        Toast.makeText(context, "Internet disconnected", Toast.LENGTH_SHORT).show()
        SaveBoolToLocalStorage("hasNet", false, context)
    }
}
