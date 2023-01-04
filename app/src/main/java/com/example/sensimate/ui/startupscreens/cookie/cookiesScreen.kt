package com.example.sensimate.ui.InitialStartPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.db
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.*


@Composable
@Preview(showBackground = true)
fun CookieScreenPreview() {
    CookiesScreen(rememberNavController())
}


@Composable
fun CookiesScreen(navController: NavController) {
    val checkedState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Spacer(modifier = Modifier.size(20.dp))
            Image(
                painter = painterResource(
                    id = R.drawable.sentimatelogo
                ),
                contentDescription = "",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "SensiMate",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = manropeFamily
            )

            Spacer(modifier = Modifier.size(0.dp, 150.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                    },
                    colors = CheckboxDefaults
                        .colors(
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White,
                            checkedColor = Purple500,

                            disabledColor = Color.White,
                            disabledIndeterminateColor = Color.White,
                        )
                )
                Text(
                    text = "Continue using cookie",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontFamily = manropeFamily
                )
                Text(
                    text = " *",
                    color = Color.Red,
                    fontWeight = FontWeight.Light,
                    fontFamily = manropeFamily
                )
            }

            Text(
                text = "To make our system work, " +
                        "we need to place a single technical " +
                        "cookie on your device. This cookie contains the " +
                        "time you entered our app, nothing more.",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.size(250.dp, 100.dp),
                fontFamily = manropeFamily
            )
            Spacer(modifier = Modifier.size(20.dp))

            val context = LocalContext.current

            Button(

                enabled = checkedState.value,

                onClick = {

                    SaveBoolToLocalStorage(
                        key = "acceltedCookie",
                        value = true,
                        context = context
                    )

                    navController.navigate(Screen.Login.route) {
                        restoreState = true
                    }
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                modifier = Modifier.size(200.dp, 50.dp)

            ) {
                Text(
                    text = "Continue",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = manropeFamily
                )
            }

            Spacer(modifier = Modifier.size(30.dp))

            var abe = ""
            Button(
                onClick = { System.exit(0) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = RedColor),
                modifier = Modifier.size(200.dp, 50.dp)

            ) {
                Text(
                    text = "Exit",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = manropeFamily
                )
            }
        }
    }
}

@Composable
fun MySensimateLogo() {
    Image(
        painter = painterResource(
            id = R.drawable.sentimatelogo
        ),
        contentDescription = "",
        modifier = Modifier.size(120.dp)
    )
}