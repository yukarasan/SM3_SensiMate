package com.example.sensimate.data

import android.content.Context

import android.content.SharedPreferences

import android.util.Log

import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview



fun SaveBoolToLocalStorage(key: String, value: Boolean, context: Context) {
    //val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean(key, value)
        apply()
    }
}


fun SaveStringToLocalStorage(key: String, value: String, context: Context) {

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString(key, value)
        apply()
    }
}


fun getStringFromLocalStorage(key: String, context: Context): String {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}


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
