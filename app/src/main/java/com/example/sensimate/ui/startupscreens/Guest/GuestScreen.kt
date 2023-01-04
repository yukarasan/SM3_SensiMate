package com.example.sensimate.ui.startupscreens.Guest

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.InitialStartPage.ChooseBirthDate
import com.example.sensimate.ui.InitialStartPage.DropDownMenu
import com.example.sensimate.ui.InitialStartPage.MySensimateLogo
import com.example.sensimate.ui.InitialStartPage.SignMenus
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground
import com.example.sensimate.ui.startupscreens.signUp.myButton
import com.example.sensimate.ui.startupscreens.signUp.textFieldWithImage
import com.example.sensimate.ui.theme.PurpleButtonColor

@Preview(showBackground = true)
@Composable
fun GuestScreenPrev() {
    GuestScreen(navController = rememberNavController())
}

@Composable
fun GuestScreen(navController: NavController) {

    InitialStartBackground()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        item {
            Spacer(modifier = Modifier.size(50.dp))
            SignMenus(navController, Screen.Guest)

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

            val selectedGender = remember { mutableStateOf("") }
            var postalCode by remember { mutableStateOf("") }

            textFieldWithImage(
                painterResource(id = R.drawable.locationicon),
                text = postalCode,
                onValueChange = {
                    if (it.length <= 4) {
                        postalCode = it
                    }
                },
                "Postal code"
            )
            DropDownMenu(selectedGender)

            val myYear = remember { mutableStateOf("") }
            val myMonth = remember { mutableStateOf("") }
            val myDay = remember { mutableStateOf("") }
            ChooseBirthDate(
                LocalContext.current,
                myYear = myYear,
                myMonth = myMonth,
                myDay = myDay
            )

            Spacer(modifier = Modifier.size(30.dp))
            val context = LocalContext.current

            myButton(color = Color.White,
                title = "Continue as guest",
                PurpleButtonColor,
                onClick = {

                    if (selectedGender.value == "" || postalCode.length < 4 || myYear.value == "") {
                        Toast.makeText(
                            context,
                            "Remember to fill out all info",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.popBackStack()

                        Database.loginAnonymously(context)
                        navController.navigate(Screen.EventScreen.route)
                    }

                }
            )
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}