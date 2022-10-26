package com.example.sensimate.ui.InitialStartPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.FaceBookColor
import com.example.sensimate.ui.theme.PurpleButtonColor

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun ChooseSignUpScreen() {
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




    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = PurpleButtonColor
                ),
            modifier = Modifier.size(320.dp, 50.dp),
            shape = CircleShape,
        ) {
            Text(
                "Sign up using e-mail",
                color = Color.White,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
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
        Surface(
            modifier = Modifier.size(180.dp, 50.dp),
            color = Color.White,
            shape = CircleShape,
        ) {
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.locationicon),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 0.dp, end = 10.dp)
                            .size(34.dp),
                    )
                    var test = "";

                    TextField(
                        value = test,
                        onValueChange = { test = it },
                        placeholder = {
                            Text(
                                text = ("Postal code"),
                                fontSize = 17.sp,
                                textAlign = TextAlign.Center
                            )
                        },

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

        Spacer(modifier = Modifier.size(28.dp))


        // Continue with Facebook button
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = FaceBookColor,
                ),
            modifier = Modifier.size(320.dp, 50.dp),
            shape = CircleShape,


            ) {
            Image(
                painter = painterResource(
                    id = R.drawable.facebook
                ),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.size(15.dp))

            Text(
                "Continue with Facebook",
                color = Color.White,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
            )
        }

        Spacer(modifier = Modifier.size(28.dp))


        //Continue with google button
        Surface(
            modifier = Modifier.size(320.dp, 50.dp),
            color = Color.White,
            shape = CircleShape,
            onClick = { print("abe") },
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
                        painter = painterResource(
                            id = R.drawable.google
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 20.dp)
                    )

                    Text(
                        "Continue with Google",
                        color = Color.Gray,
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

}