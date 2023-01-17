package com.example.sensimate.ui.startupscreens.noNet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.broadcastreceivers.InternetBroadcastReceiver
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.auth
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.components.InitialStartBackground
import com.example.sensimate.ui.startupscreens.components.myButton
import com.example.sensimate.ui.theme.Purple200

@Preview(showBackground = true)
@Composable
fun NoWifiPreview() {
    NoWifiScreen(navController = rememberNavController())
}

/**
@author Hussein el-zein
Function that displays the no internet connection screen and handles the logic for reloading the app.
@param navController Navigation controller for navigating between screens.
 */
@Composable
fun NoWifiScreen(navController: NavController) {

    InitialStartBackground()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.wifiicon
            ),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .blur(1.dp)
                .alpha(0.5f),
        )

        Text(
            stringResource(id = R.string.noNet),
            color = Color.White,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
        )

        Spacer(modifier = Modifier.size(40.dp))

        val context = LocalContext.current

        myButton(Color.White, title = "Reload", Purple200, onClick = {
            InternetBroadcastReceiver(context)

            if (getBooleanFromLocalStorage("hasNet", context)) {

                if (getBooleanFromLocalStorage(
                        "acceptedCookie",
                        context
                    )
                ) {
                    if (auth.currentUser != null) {

                        if (getBooleanFromLocalStorage("isEmployee", context)) {

                            navController.navigate(Screen.EventScreenEmployee.route)
                        } else {
                            navController.navigate(Screen.EventScreen.route)
                        }

                    } else {
                        SaveBoolToLocalStorage(
                            "isEmployee",
                            false,
                            context
                        )
                        navController.navigate(Screen.Login.route)

                    }

                } else {
                    navController.navigate(Screen.CookieScreen.route)
                }
            }
        })
    }
}