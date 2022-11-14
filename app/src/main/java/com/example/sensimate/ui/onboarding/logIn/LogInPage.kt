package com.example.sensimate.ui.InitialStartPage

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sensimate.R
import com.example.sensimate.ui.onboarding.signUp.InitialStartBackground
import com.example.sensimate.ui.onboarding.signUp.buttonWithImage
import com.example.sensimate.ui.onboarding.signUp.myButton
import com.example.sensimate.ui.theme.FaceBookColor
import com.example.sensimate.ui.theme.PurpleButtonColor

@Preview(showBackground = true)
@Composable
fun LogInMail() {

    InitialStartBackground()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.size(250.dp))

        //email button
        var email by remember { mutableStateOf("") }
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
        var password by remember { mutableStateOf("") }
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

        myButton(color = Color.White, title = "Log in", PurpleButtonColor)

        Spacer(modifier = Modifier.size(250.dp))

        buttonWithImage(
            bgcolor = FaceBookColor,
            text = "Log in with Facebook",
            painter = painterResource(id = R.drawable.facebook),
            Color.White
        )

        Spacer(modifier = Modifier.size(20.dp))

        //Continue with google button
        buttonWithImage(
            bgcolor = Color.White,
            text = "Log in with Google",
            painter = painterResource(id = R.drawable.google),
            Color.Gray
        )


        Spacer(modifier = Modifier.size(200.dp))

    }
}