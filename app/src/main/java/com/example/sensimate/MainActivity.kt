package com.example.sensimate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext

/**
 * MainActivity is the entry point of the app. And it sets the content to be the App composable.
 */
    class MainActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(context = LocalContext.current)
        }
    }
}




