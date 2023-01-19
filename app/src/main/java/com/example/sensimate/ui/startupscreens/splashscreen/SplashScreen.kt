package com.example.sensimate.ui.startupscreens.splashscreen

import android.provider.CalendarContract.Colors
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.auth
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import kotlinx.coroutines.delay

/**
 * Code Inspriration: https://www.geeksforgeeks.org/animated-splash-screen-in-android-using-jetpack-compose/
 * @author Anshjyot Singh
 */
@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val context = LocalContext.current

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        // Customize the delay time
        delay(1000L)


        val screen =
            if (!getBooleanFromLocalStorage("hasNet", context)) {
                Screen.SplashScreen
                Screen.NoWifi

            } else if (getBooleanFromLocalStorage(
                    "acceptedCookie",
                    context
                )
            ) {
                if (auth.currentUser != null) {

                    if (getBooleanFromLocalStorage("isEmployee", context)) {
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
        


        navController.navigate(screen.route)
    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(colors = listOf(
            DarkPurple, BottomGradient)))
    ) {
        // Change the logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value).fillMaxSize().padding(100.dp)
        )
    }
}
