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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.SaveStringToLocalStorage
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.InitialStartPage.ChooseBirthDate
import com.example.sensimate.ui.InitialStartPage.DropDownMenu
import com.example.sensimate.ui.InitialStartPage.MySensimateLogo
import com.example.sensimate.ui.InitialStartPage.SignMenus
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.ForgotPassword.StartProfileViewModel
import com.example.sensimate.ui.startupscreens.components.InitialStartBackground
import com.example.sensimate.ui.startupscreens.components.myButton
import com.example.sensimate.ui.startupscreens.components.textFieldWithImage
import com.example.sensimate.ui.theme.PurpleButtonColor

@Preview(showBackground = true)
@Composable
fun GuestScreenPrev() {
    GuestScreen(navController = rememberNavController(), viewModel())
}

@Composable
fun GuestScreen(
    navController: NavController,
    startProfileViewModel: StartProfileViewModel
) {

    val state = startProfileViewModel._uiState.collectAsState()

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
                text = stringResource(id = R.string.myName),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = manropeFamily
            )

            Spacer(modifier = Modifier.size(100.dp))

            textFieldWithImage(
                painterResource(id = R.drawable.locationicon),
                text = state.value.postalCode.value,
                onValueChange = {
                    if (it.length <= 4) {
                        startProfileViewModel.changePostalCode(it)
                    }
                },
                stringResource(id = R.string.postalcode)
            )
            DropDownMenu(state.value.gender)

            ChooseBirthDate(
                LocalContext.current,
                myYear = state.value.yearBorn,
                myMonth = state.value.monthBorn,
                myDay = state.value.dayBorn
            )

            Spacer(modifier = Modifier.size(30.dp))
            val context = LocalContext.current

            myButton(color = Color.White,
                title = stringResource(id = R.string.continueGuest),
                PurpleButtonColor,
                onClick = {
                    startProfileViewModel.loginAsGuest(
                        navController = navController,
                        context = context
                    )
                }
            )
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}