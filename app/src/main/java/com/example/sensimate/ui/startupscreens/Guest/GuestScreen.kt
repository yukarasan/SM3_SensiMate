package com.example.sensimate.ui.startupscreens.Guest

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.ui.InitialStartPage.SignMenus
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground

@Composable
fun GuestScreen(navController: NavController) {

    InitialStartBackground()

    Spacer(modifier = Modifier.size(100.dp))
    SignMenus(navController, Screen.Guest)
}