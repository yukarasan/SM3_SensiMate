package com.example.sensimate.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

/**
@author Hussein El-Zein
SaveBoolToLocalStorage function that saves a Boolean value to local storage
@param key - the key to be used to store the value in local storage
@param value - the Boolean value to be saved
@param context - the context of the application
 */
fun SaveBoolToLocalStorage(key: String, value: Boolean, context: Context) {
    //val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean(key, value)
        apply()
    }
}


/**
@author Hussein El-Zein
SaveStringToLocalStorage function that saves a String value to local storage
@param key - the key to be used to store the value in local storage
@param value - the String value to be saved
@param context - the context of the application
 */
fun SaveStringToLocalStorage(key: String, value: String, context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString(key, value)
        apply()
    }
}

/**
@author Hussein El-Zein
getStringFromLocalStorage function that retrieves a String value from local storage
@param key - the key used to store the value in local storage
@param context - the context of the application
@return the value stored in local storage with the given key
 */
fun getStringFromLocalStorage(key: String, context: Context): String {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}


/**
@author Hussein El-Zein
getBooleanFromLocalStorage function that retrieves a Boolean value from local storage
@param key - the key used to store the value in local storage
@param context - the context of the application
@return the value stored in local storage with the given key
 */
fun getBooleanFromLocalStorage(key: String, context: Context): Boolean {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, false)
}

@Preview
@Composable
fun TestLocalStorage() {
    val context = LocalContext.current

    SaveBoolToLocalStorage(
        key = "isLoggedIn",
        value = true,
        context
    )

    Log.d(
        "isLoggedIn?",
        getBooleanFromLocalStorage(key = "isLoggedIn", context).toString()
    )
}