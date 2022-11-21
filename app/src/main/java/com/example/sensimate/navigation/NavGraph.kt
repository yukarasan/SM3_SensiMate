package com.example.sensimate.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sensimate.ui.Event.EventUiState
import com.example.sensimate.ui.Event.createEvent.CreateEventScreen
import com.example.sensimate.ui.Event.createEvent.QuestionPageScreen
import com.example.sensimate.ui.InitialStartPage.CookiesScreen
import com.example.sensimate.ui.Event.extendedEvent.ExtendedEvent
import com.example.sensimate.ui.InitialStartPage.SignUpUsingMail
import com.example.sensimate.ui.home.EventScreen
import com.example.sensimate.ui.profile.EditProfileScreen
import com.example.sensimate.ui.profile.ProfileScreen
import com.example.sensimate.ui.profile.editProfile.*
import com.example.sensimate.ui.startupscreens.signUp.ChooseSignUpScreen

@Composable
fun SetupNavGraph(navController: NavHostController, eventUIState: EventUiState) {
    NavHost(navController = navController, startDestination = Screen.ProfileScreen.route) {      // Screen.CookieScreen.route
        composable(route = Screen.CookieScreen.route) {
            CookiesScreen(navController = navController, uiState = eventUIState)
        }
        composable(route = Screen.ChooseSignUpScreen.route){
            BackHandler(true) {
                // Do nothing
            }
            ChooseSignUpScreen(navController = navController, uiState = eventUIState)
        }
        composable(route = Screen.SignUpWithMail.route){
            SignUpUsingMail(navController = navController, uiState = eventUIState)
        }
        composable(route = Screen.EventScreen.route) {
            EventScreen(navController = navController, uiState = eventUIState)
        }
        composable(route = Screen.ExtendedEventScreen.route) {
            ExtendedEvent()
        }
        composable(route = Screen.CreateEventScreen.route) {
            CreateEventScreen()
        }
        composable(route = Screen.QuestionPageScreen.route) {
            QuestionPageScreen()
        }

        // Profile navigation:
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }
        composable(route = Screen.EditPostalScreen.route) {
            EditPostalCodeScreen(navController = navController)
        }
        composable(route = Screen.EditAgeScreen.route) {
            EditAgeScreen(navController = navController)
        }
        composable(route = Screen.EditEmailScreen.route) {
            EditEmailScreen(navController = navController)
        }
        composable(route = Screen.EditPasswordScreen.route) {
            EditPasswordScreen(navController = navController)
        }
        composable(route = Screen.EditGenderScreen.route) {
            EditGenderScreen(navController = navController)
        }
    }
}