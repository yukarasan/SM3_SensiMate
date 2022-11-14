package com.example.sensimate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sensimate.ui.Event.EventUiState
import com.example.sensimate.ui.InitialStartPage.CookiesScreen
import com.example.sensimate.ui.event.ExtendedEvent
import com.example.sensimate.ui.home.EventScreen
import com.example.sensimate.ui.profile.ProfileScreen

@Composable
fun SetupNavGraph(navController: NavHostController, eventUIState: EventUiState) {
    NavHost(navController = navController, startDestination = Screen.CookieScreen.route) {      // Screen.CookieScreen.route
        composable(route = Screen.CookieScreen.route) {
            CookiesScreen()
        }
        composable(route = Screen.EventScreen.route) {
            EventScreen(navController = navController, uiState = eventUIState)
        }
        composable(route = Screen.ExtendedEventScreen.route) {
            ExtendedEvent()
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}