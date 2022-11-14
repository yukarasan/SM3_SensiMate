package com.example.sensimate.navigation

sealed class Screen(val route: String) {
    object CookieScreen: Screen(route = "cookie")
    object EventScreen: Screen(route = "event")
    object ExtendedEventScreen: Screen(route = "extendedEvent")
}
