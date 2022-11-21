package com.example.sensimate.navigation

sealed class Screen(val route: String) {
    object CookieScreen: Screen(route = "cookie")
    object ChooseSignUpScreen: Screen(route = "chooseSignup")
    object SignUpWithMail: Screen(route = "mailSignup")
    object Login: Screen(route = "login")
    object EventScreen: Screen(route = "event")
    object ExtendedEventScreen: Screen(route = "extendedEvent")
    object ProfileScreen: Screen(route = "profile")
    object CreateEventScreen: Screen(route = "createEvent")
    object QuestionPageScreen: Screen(route = "questionPage")
    object Registerscreen: Screen(route = "register")
    object EditAgeScreen: Screen(route = "editAge")
    object EditEmailScreen: Screen(route = "editEmail")
    object EditGenderScreen: Screen(route = "editGender")
    object EditPasswordScreen: Screen(route = "editPassword")
    object EditPostalScreen: Screen(route = "editPostal")
    object EditProfileScreen: Screen(route = "editProfile")
    object Survey: Screen(route = "survey1")
    object Survey2: Screen(route = "survey2")
    object Survey3: Screen(route = "survey3")
    object Survey4: Screen(route = "survey4")
}
