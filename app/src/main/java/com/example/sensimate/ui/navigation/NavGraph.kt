package com.example.sensimate.ui.navigation

import EditEvent
import EditPage
import EditSurvey
import EditSurveyPage
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sensimate.ui.Event.EventUiState
import com.example.sensimate.ui.Event.createEvent.CreateEventScreen
import com.example.sensimate.ui.Event.createEvent.CreateMultpleChoiceQuestionScreen
import com.example.sensimate.ui.Event.createEvent.QuestionPageScreen
import com.example.sensimate.ui.InitialStartPage.CookiesScreen
import com.example.sensimate.ui.Event.extendedEvent.ExtendedEvent
import com.example.sensimate.ui.InitialStartPage.LogInMail
import com.example.sensimate.ui.InitialStartPage.SignUpUsingMail
import com.example.sensimate.ui.home.EventScreen
import com.example.sensimate.ui.home.EventScreenEmployee
import com.example.sensimate.ui.profile.EditProfileScreen
import com.example.sensimate.ui.profile.ProfileScreen
import com.example.sensimate.ui.profile.editProfile.*
import com.example.sensimate.ui.startupscreens.signUp.ChooseSignUpScreen
import com.example.sensimate.ui.survey.Survey
import com.example.sensimate.ui.survey.Survey2
import com.example.sensimate.ui.survey.Survey3
import com.example.sensimate.ui.survey.Survey4

@Composable
fun SetupNavGraph(navController: NavHostController, eventUIState: EventUiState) {
    NavHost(navController = navController, startDestination = Screen.Survey.route) {      // Screen.CookieScreen.route
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
            EventScreen(navController = navController)
        }
        composable(route = Screen.ExtendedEventScreen.route) {
            ExtendedEvent(navController = navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.CreateEventScreen.route) {
            CreateEventScreen(navController = navController)
        }
        composable(route = Screen.QuestionPageScreen.route) {
            QuestionPageScreen(navController = navController)
        }
        composable(route = Screen.CreateMultpleChoiceQuestionScreen.route) {
            CreateMultpleChoiceQuestionScreen(navController = navController)
        }
        composable(route = Screen.EditEvent.route) {
            EditEvent(navController = navController)
        }
        composable(route = Screen.EditPage.route) {
            EditPage(navController = navController)
        }
        composable(route = Screen.EditSurvey.route) {
            EditSurvey(navController = navController)
        }
        composable(route = Screen.EditSurveyPage.route) {
            EditSurveyPage(navController = navController)
        }
        composable(route = Screen.EventScreenEmployee.route) {
            EventScreenEmployee(navController = navController)
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


        // SURVERY NAVIGATION
        composable(route = Screen.Survey.route) {
            Survey(navController = navController)
        }
        composable(route = Screen.Survey2.route) {
            Survey2(navController = navController)

        }
        composable(route = Screen.Survey3.route) {
            Survey3(navController = navController)
        }
        composable(route = Screen.Survey4.route) {
            Survey4(navController = navController)
        }
    }
}
