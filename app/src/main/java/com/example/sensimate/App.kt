package com.example.sensimate

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.ui.navigation.SetupNavGraph
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

@Composable
fun App(context: Context) {
    // Navigation
    val navController: NavHostController = rememberNavController()
    SetupNavGraph(navController = navController)

    // Locking the screen rotation
    val activity = context as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

    val systemUiController = rememberSystemUiController()

    // Setting the color of the status bar and navigation bar
    SideEffect {
        systemUiController.setStatusBarColor(
            color = DarkPurple,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = BottomGradient,
            darkIcons = false
        )
    }
}