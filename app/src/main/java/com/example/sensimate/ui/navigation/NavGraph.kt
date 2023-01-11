package com.example.sensimate.ui.navigation

import AnswerViewModel
import EditEvent
import EditPage
import EditSurvey
import EditSurveyPage
import TextAnswerViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.sensimate.data.EventViewModel
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.auth
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.ui.Event.EventUiState
import com.example.sensimate.ui.Event.createEvent.*
import com.example.sensimate.ui.InitialStartPage.CookiesScreen
import com.example.sensimate.ui.Event.extendedEvent.ExtendedEvent
import com.example.sensimate.ui.InitialStartPage.LogInMail
import com.example.sensimate.ui.InitialStartPage.SignUpUsingMail
import com.example.sensimate.ui.home.EventScreen
import com.example.sensimate.ui.home.EventScreenEmployee
import com.example.sensimate.ui.profile.EditProfileScreen
import com.example.sensimate.ui.profile.ProfileScreen
import com.example.sensimate.ui.profile.editProfile.*
import com.example.sensimate.ui.startupscreens.ForgotPassword.ForgotPassword
import com.example.sensimate.ui.startupscreens.ForgotPassword.StartProfileViewModel
import com.example.sensimate.ui.startupscreens.Guest.GuestScreen
import com.example.sensimate.ui.survey.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController, eventUIState: EventUiState) {

    val questionViewModel = QuestionViewModel()
    val eventViewModel = EventViewModel()
    val startProfileViewModel = StartProfileViewModel()
    val createEventViewModel = CreateEventViewModel()
    val answerViewModel = AnswerViewModel()
    val textAnswerViewModel = TextAnswerViewModel()

    val context = LocalContext.current

    val screen =
        if (getBooleanFromLocalStorage(
                "acceptedCookie",
                context
            )
        ) {
            if (auth.currentUser != null) {
                if (getBooleanFromLocalStorage( ///FIX DEN HER. TODO: Hussein
                        "isEmployee",
                        context
                    )
                ) {
                    Screen.EventScreenEmployee
                } else {
                    Screen.EventScreen
                }

            } else {
                SaveBoolToLocalStorage(
                    "isEmployee",
                    false,
                    context
                )
                Screen.Login
            }

        } else {
            Screen.CookieScreen
        }

    NavHost(
        navController = navController,
        startDestination = screen.route
    ) {      // Screen.CookieScreen.route
        //Screens when starting up
        composable(route = Screen.CookieScreen.route) {
            CookiesScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LogInMail(navController = navController, startProfileViewModel = startProfileViewModel)
        }
        composable(route = Screen.SignUpWithMail.route) {
            SignUpUsingMail(navController = navController, startProfileViewModel = startProfileViewModel)
        }
        composable(route = Screen.Guest.route) {
            GuestScreen(navController = navController, startProfileViewModel = startProfileViewModel)
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPassword(navController = navController, startProfileViewModel = startProfileViewModel)
        }
        ///

        composable(route = Screen.EventScreen.route) {
            EventScreen(navController = navController, eventViewModel = eventViewModel)
        }

        composable(route = Screen.ExtendedEventScreen.route){
            ExtendedEvent(
                navController = navController, eventViewModel = eventViewModel
            )
        }

        /*
        composable(
            route = Screen.ExtendedEventScreen.route,
            arguments = listOf(
                navArgument(TITLE_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(TIME_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(LOCATION_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(ALLERGENS) {
                    type = NavType.StringType
                },
                navArgument(DESCRIPTION_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(SURVEYCODE) {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            ExtendedEvent(
                navController = navController,
                title = backStackEntry.arguments?.getString(TITLE_OF_EVENT).toString(),
                time = backStackEntry.arguments?.getString(TIME_OF_EVENT).toString(),
                location = backStackEntry.arguments?.getString(LOCATION_OF_EVENT).toString(),
                allergens = backStackEntry.arguments?.getString(ALLERGENS).toString(),
                description = backStackEntry.arguments?.getString(DESCRIPTION_OF_EVENT).toString(),
                surveyCode = backStackEntry.arguments?.getString(SURVEYCODE).toString()
            )
        }
         */
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.CreateEventScreen.route) {
            CreateEventScreen(navController = navController, createEventViewModel = createEventViewModel)
        }
        composable(route = Screen.QuestionPageScreen.route) {
            QuestionPageScreen(navController = navController)
        }
        composable(route = Screen.CreateMultpleChoiceQuestionScreen.route) {
            CreateMultpleChoiceQuestionScreen(navController = navController, answerViewModel = answerViewModel)
        }
        composable(route = Screen.CreateTextAnswerQuestionScreen.route) {
            CreateTextAnswerQuestionScreen(navController = navController, textAnswerViewModel = textAnswerViewModel)
        }
        /*
        composable(
            route = Screen.EditEvent.route,
            arguments = listOf(
                navArgument(TITLE_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(TIME_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(LOCATION_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(ALLERGENS) {
                    type = NavType.StringType
                },
                navArgument(DESCRIPTION_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(SURVEYCODE) {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            EditEvent(
                navController = navController,
                title = backStackEntry.arguments?.getString(TITLE_OF_EVENT).toString(),
                time = backStackEntry.arguments?.getString(TIME_OF_EVENT).toString(),
                location = backStackEntry.arguments?.getString(LOCATION_OF_EVENT).toString(),
                allergens = backStackEntry.arguments?.getString(ALLERGENS).toString(),
                description = backStackEntry.arguments?.getString(DESCRIPTION_OF_EVENT).toString(),
                surveyCode = backStackEntry.arguments?.getString(SURVEYCODE).toString(),
            )
        }
        
         */
        
        composable(Screen.EditEvent.route){
            EditEvent(navController = navController, eventViewModel = eventViewModel)
        }

        /*
        composable(
            route = Screen.EditPage.route,
            arguments = listOf(
                navArgument(TITLE_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(TIME_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(LOCATION_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(ALLERGENS) {
                    type = NavType.StringType
                },
                navArgument(DESCRIPTION_OF_EVENT) {
                    type = NavType.StringType
                },
                navArgument(SURVEYCODE) {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            EditPage(
                navController = navController,
                title = backStackEntry.arguments?.getString(TITLE_OF_EVENT).toString(),
                time = backStackEntry.arguments?.getString(TIME_OF_EVENT).toString(),
                location = backStackEntry.arguments?.getString(LOCATION_OF_EVENT).toString(),
                allergens = backStackEntry.arguments?.getString(ALLERGENS).toString(),
                description = backStackEntry.arguments?.getString(DESCRIPTION_OF_EVENT)
                    .toString(),
                surveyCode = backStackEntry.arguments?.getString(SURVEYCODE).toString(),
            )
        }
        
         */
        
        composable(route = Screen.EditPage.route){
            EditPage(navController = navController, eventViewModel = eventViewModel)
        }
        composable(route = Screen.EditSurvey.route) {
            EditSurvey(navController = navController, questionViewModel)
        }
        composable(route = Screen.EditSurveyPage.route) {
            EditSurveyPage(navController = navController)
        }
        composable(route = Screen.EventScreenEmployee.route) {
            EventScreenEmployee(navController = navController, eventViewModel = eventViewModel)
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

        composable(route =Screen.SurveyCreator.route) {
            SurveyCreator(
                navController = navController,
                questionViewModel = questionViewModel,
                eventViewModel = eventViewModel
            )
        }

        // SURVERY NAVIGATION
        composable(route = Screen.Survey.route) {
            Survey(
                navController = navController,
                title = ""
            )
        }
        composable(route = Screen.Survey2.route) {
            Survey2(navController = navController, title = "", questionViewModel = questionViewModel)

        }
        composable(route = Screen.Survey3.route) {
            Survey3(navController = navController, title = "", questionViewModel = questionViewModel)
        }
        composable(route = Screen.Survey4.route) {
            Survey4(navController = navController, title = "", questionViewModel = questionViewModel)
        }
        /*composable(route = Screen) {
            SurveyCreator(navController = navController, questionViewModel = questionViewModel, eventViewModel = eventViewModel)
        }

         */
    }
}