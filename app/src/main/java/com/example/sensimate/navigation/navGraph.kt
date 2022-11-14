package com.example.sensimate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sensimate.ui.Event.EventUIState
import com.example.sensimate.ui.InitialStartPage.CookiesScreen
import com.example.sensimate.ui.event.ExtendedEvent

class navGraph {
    @Composable
    fun SetupNavGraph(navController: NavHostController, eventUIState: EventUIState) {
        NavHost(navController = navController, startDestination = Screen.CookieScreen.route) {      // Screen.CookieScreen.route
            composable(route = Screen.CookieScreen.route) {
                CookiesScreen()
            }
            composable(route = Screen.ExtendedEventScreen.route) {
                ExtendedEvent()
            }
        }
    }
}