package com.example.sensimate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.ui.Event.EventUiState
import com.example.sensimate.ui.Event.createEvent.CreateEventScreen
import com.example.sensimate.ui.Event.createEvent.QuestionPageScreen
import com.example.sensimate.ui.InitialStartPage.CookiesScreen
import com.example.sensimate.ui.Event.extendedEvent.ExtendedEvent
import com.example.sensimate.ui.InitialStartPage.LogInMail
import com.example.sensimate.ui.InitialStartPage.SignUpUsingMail
import com.example.sensimate.ui.home.EventScreen
import com.example.sensimate.ui.profile.ProfileScreen
import com.example.sensimate.ui.startupscreens.signUp.ChooseSignUpScreen

@Composable
fun SetupNavGraph(navController: NavHostController, eventUIState: EventUiState) {
    NavHost(navController = navController, startDestination = Screen.CookieScreen.route) {      // Screen.CookieScreen.route
        composable(route = Screen.CookieScreen.route) {
            CookiesScreen(navController = navController)
        }
        composable(route = Screen.ChooseSignUpScreen.route){
            ChooseSignUpScreen(navController = navController)
        }
        composable(route = Screen.SignUpWithMail.route){
            SignUpUsingMail(navController = navController)
        }
        composable(route = Screen.Login.route){
            LogInMail(navController = navController)
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
        composable(route = Screen.CreateEventScreen.route) {
            CreateEventScreen()
        }
        composable(route = Screen.QuestionPageScreen.route) {
            QuestionPageScreen()
        }
    }
}