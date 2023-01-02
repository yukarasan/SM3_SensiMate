package com.example.sensimate.ui.startupscreens.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

        Spacer(modifier = Modifier.size(20.dp))

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

        //Postal code button
        var textFieldState by remember { mutableStateOf("") }
        textFieldWithImage(
            painterResource(id = R.drawable.locationicon),
            text = textFieldState,
            onValueChange = { textFieldState = it },
            "Postal code"
        )

        Spacer(modifier = Modifier.size(28.dp))

        // Continue with Facebook button
        buttonWithImage(
            bgcolor = FaceBookColor,
            text = "Continue with Facebook",
            painter = painterResource(id = R.drawable.facebook),
            Color.White,
            onClick = {}
        )

        Spacer(modifier = Modifier.size(28.dp))

        //Continue with google button
        buttonWithImage(
            bgcolor = Color.White,
            text = "Continue with Google",
            painter = painterResource(id = R.drawable.google),
            Color.Gray,
            onClick = {}
        )

        Spacer(modifier = Modifier.size(28.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already a member?",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = manropeFamily
            )
            Text(
                text = "  Sign in",
                color = Color(0xFF5978D3),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = manropeFamily,
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.Login.route
                    )
                }
            )
        }

    }
    Spacer(modifier = Modifier.size(28.dp))


}

@Composable
fun textFieldWithImage(
    painter: Painter,
    text: String,
    onValueChange: (String) -> Unit,
    placeHolder: String
) {

    Surface(
        modifier = Modifier.size(180.dp, 50.dp),
        color = Color.White,
        shape = CircleShape,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Postal code image with textfield
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
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
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.fillMaxHeight()
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

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
            fontWeight = FontWeight.Bold,
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