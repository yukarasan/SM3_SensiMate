package com.example.sensimate.ui.startupscreens.ForgotPassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.data.Database
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.InitialStartPage.MySensimateLogo
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.InitialStartPage.SignMenus
import com.example.sensimate.ui.InitialStartPage.showLoading
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground
import com.example.sensimate.ui.startupscreens.signUp.myButton
import com.example.sensimate.ui.theme.PurpleButtonColor

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    ForgotPassword(rememberNavController())
}

@Composable
fun ForgotPassword(navController: NavController) {
    InitialStartBackground()
    var email by remember { mutableStateOf("") }
    val showLoading = remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 10.dp
                )
            ) {
                OrangeBackButton {
                    navController.popBackStack()
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                MySensimateLogo()
                Text(
                    text = "SensiMate",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = manropeFamily
                )

                Spacer(modifier = Modifier.size(80.dp))


                MyTextField(
                    text = email,
                    textSize = 15,
                    onValueChange = { email = it },
                    placeHolder = "Enter E-mail",
                    width = 300,
                    height = 51,
                    KeyboardType.Email,
                    visualTransformation = VisualTransformation.None,
                    Color.DarkGray,
                    Color.White,
                    Color.Gray
                )
            }

            val context = LocalContext.current
            myButton(color = Color.White,
                title = "Sign up",
                PurpleButtonColor,
                onClick = {Database.forgotPassword(email = email, context, showLoading)}
            )

        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        showLoading(showLoading)
    }
}
