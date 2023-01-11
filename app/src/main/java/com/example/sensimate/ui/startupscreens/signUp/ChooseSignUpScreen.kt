package com.example.sensimate.ui.startupscreens.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.Event.EventUiState
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.FaceBookColor
import com.example.sensimate.ui.theme.PurpleButtonColor

@Composable
fun InitialStartBackground() {

    Screen.EventScreen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )

    Image(
        painter = painterResource(
            id = R.drawable.sentimatelogo
        ),
        contentDescription = "",
        modifier = Modifier
            .size(2700.dp)
            .blur(1.dp)
            .alpha(0.2f),
        contentScale = ContentScale.Crop,

        )
}

@Composable
fun ChooseSignUpScreen(navController: NavController) {

    InitialStartBackground()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        myButton(
            color = Color.White,
            title = "Sign up with e-mail",
            PurpleButtonColor,
            onClick = {
                navController.navigate(Screen.SignUpWithMail.route) {
                }
            }
        )

        //Row start for "--or--"
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.line),
                contentDescription = "",
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.size(13.dp))

            Text(
                "Or",
                color = Color.White,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.size(13.dp))

            Image(
                painter = painterResource(id = R.drawable.line),
                contentDescription = "",
                modifier = Modifier.size(90.dp)
            )
        }//Or row end

        Spacer(modifier = Modifier.size(28.dp))

        Spacer(modifier = Modifier.size(28.dp))

        /////////navController.navigate(Screen.Login.route)

    }
    Spacer(modifier = Modifier.size(28.dp))


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun textFieldWithImage(
    painter: Painter,
    text: String,
    onValueChange: (String) -> Unit,
    placeHolder: String
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.size(180.dp, 50.dp),
        color = Color.White,
        shape = CircleShape,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 0.dp, end = 10.dp)
                        .size(34.dp),
                )

                TextField(
                    value = text,
                    onValueChange = onValueChange,
                    placeholder = {
                        Text(
                            text = placeHolder,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.fillMaxHeight()
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),

                    singleLine = true,

                    modifier = Modifier.fillMaxSize(),

                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),

                    )
            }
        }


    }


}


@Composable
fun myButton(
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
        modifier = Modifier.size(320.dp, 50.dp),
        shape = CircleShape,
    ) {
        Text(
            title,
            color = color,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun buttonWithImage(
    bgcolor: Color,
    text: String,
    painter: Painter,
    textColor: Color,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier.size(320.dp, 50.dp),
        color = bgcolor,
        shape = CircleShape,
        onClick = onClick,
        indication = rememberRipple()
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 20.dp)
                )

                Text(
                    text,
                    color = textColor,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    modifier = Modifier
                        .padding(start = 18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ChooseSignUpPreview() {
    ChooseSignUpScreen(
        rememberNavController()
    )
}