package com.example.sensimate

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.ui.navigation.SetupNavGraph
import com.example.sensimate.ui.Event.EventViewModel
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(
    context: Context,
    appViewModel: AppViewModel = viewModel(),
    eventViewModel: EventViewModel = EventViewModel(),
    modifier: Modifier = Modifier
) {
    // ViewModels
    val eventUiState by eventViewModel.eventUiState.collectAsState()
    // val profileUiState by profileViewModel.profileUiState.collectAsState()
    // val osv...

    // Navigation
    val navController: NavHostController = rememberNavController()
    SetupNavGraph(navController = navController, eventUIState = eventUiState)

    // Locking the screen rotation
    val activity = context as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

    val systemUiController = rememberSystemUiController()

    /*
     * Setting the color of the status bar and navigation bar
     */
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