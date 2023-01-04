package com.example.sensimate.ui.InitialStartPage

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground
import com.example.sensimate.ui.startupscreens.signUp.buttonWithImage
import com.example.sensimate.ui.startupscreens.signUp.myButton
import com.example.sensimate.ui.theme.FaceBookColor
import com.example.sensimate.ui.theme.PurpleButtonColor
import com.example.sensimate.ui.theme.employeelogin


@Composable
fun LogInMail(navController: NavController) {

    InitialStartBackground()

    val context = LocalContext.current
    val showLoading = remember {
        mutableStateOf(false)
    }
    val successLoggedIn = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.size(50.dp))
        SignMenus(
            navController = navController,
            Screen.Login
        )

        Spacer(modifier = Modifier.size(50.dp))
        MySensimateLogo()

        Text(
            text = "SensiMate",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = manropeFamily
        )

        Spacer(modifier = Modifier.size(100.dp))

        //email button
        var email by remember { mutableStateOf("zz@zz.zz") }
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

        Spacer(modifier = Modifier.size(20.dp))
        var password by remember { mutableStateOf("12345678") }
        MyTextField(
            text = password,
            textSize = 15,
            onValueChange = { password = it },
            placeHolder = "Enter password",
            width = 300,
            height = 51,
            KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            Color.DarkGray,
            Color.White,
            Color.Gray
        )
        Spacer(modifier = Modifier.size(28.dp))

        myButton(color = Color.White,
            title = "Sign in",
            PurpleButtonColor,

            onClick = {

                if (email == "" || password == "") {
                    Toast.makeText(
                        context, "Remember to write in a password and email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Database.logIn(
                        email = email,
                        password = password,
                        showLoading,
                        context,
                        successLoggedIn
                    )
                }
            }
        )
        Spacer(modifier = Modifier.size(28.dp))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        showLoading(showLoading)
    }

    if (successLoggedIn.value) {
        navController.navigate(Screen.EventScreen.route)
        successLoggedIn.value = false
    }

    /*
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {

        buttonWithImage(
            bgcolor = FaceBookColor,
            text = "Log in with Facebook",
            painter = painterResource(id = R.drawable.facebook),
            Color.White,
            onClick = {}
        )

        Spacer(modifier = Modifier.size(10.dp))

        //Continue with google button
        buttonWithImage(
            bgcolor = Color.White,
            text = "Log in with Google",
            painter = painterResource(id = R.drawable.google),
            Color.Gray,
            onClick = {}
        )
    }

     */

}

@Preview(showBackground = true)
@Composable
fun LogInMailPreview() {
    LogInMail(rememberNavController())
}


@Composable
fun SignMenus(
    navController: NavController,
    screen: Screen
) {
    Row(
        //horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {

        if (screen == Screen.Login) {
            MyOptionChosen(
                color = Color.White,
                title = "Sign in",
                buttonColor = PurpleButtonColor,
                onClick = {}
            )
        } else {
            MyOtherOption(
                color = Color.White,
                title = "Sign in",
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        if (screen == Screen.SignUpWithMail) {
            MyOptionChosen(
                color = Color.White,
                title = "Sign up",
                buttonColor = PurpleButtonColor,
                onClick = {}
            )
        } else {
            MyOtherOption(
                color = Color.White,
                title = "Sign up",
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignUpWithMail.route)
                }
            )
        }

        if (screen == Screen.Guest) {
            MyOptionChosen(
                color = Color.White,
                title = "Guest",
                buttonColor = PurpleButtonColor,
                onClick = {}
            )
        } else {
            MyOtherOption(
                color = Color.White,
                title = "Guest",
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Guest.route)
                }
            )
        }
    }
}

@Composable
fun MyOptionChosen(
    color: Color,
    title: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = buttonColor
            ),
        modifier = Modifier.size(103.dp, 40.dp),
        shape = CircleShape,
    ) {
        Text(
            title,
            color = color,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun MyOtherOption(
    color: Color,
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = Color.Transparent
            ),
        modifier = Modifier.size(103.dp, 40.dp),
        shape = CircleShape,
    ) {
        Text(
            title,
            color = color,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

