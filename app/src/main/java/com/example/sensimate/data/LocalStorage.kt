package com.example.sensimate.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SaveBoolToLocalStorage(key: String, value: Boolean) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with (sharedPreferences.edit()) {
        putBoolean(key, value)
        apply()
    }
}

@Composable
fun SaveStringToLocalStorage(key: String, value: String) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with (sharedPreferences.edit()) {
        putString(key, value)
        apply()
    }
}

@Composable
fun getStringFromLocalStorage(key: String): String {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}

@Composable
fun getBooleanFromLocalStorage(key: String): Boolean {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, false)
}

@Preview
@Composable
fun TestLocalStorage(){

    //SaveBoolToLocalStorage(key = "isLoggedIn", value = true)

    Log.d("isLoggedIn?", getBooleanFromLocalStorage(key = "isLoggedIn").toString())
}