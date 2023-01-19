package com.example.sensimate.broadcastreceivers

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.example.sensimate.data.SaveBoolToLocalStorage

/**
 * InternetBroadcastReceiver is a function that checks the device's internet connectivity and saves the result to local storage.
 * The function takes in a Context as a parameter, which it uses to retrieve the ConnectivityManager system service.
 * It then gets the active network information and checks if it is connected.
 * If it is connected, it calls the SaveBoolToLocalStorage function with the arguments "hasNet", true and the context.
 * @author Hussein El-Zein
*/
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
