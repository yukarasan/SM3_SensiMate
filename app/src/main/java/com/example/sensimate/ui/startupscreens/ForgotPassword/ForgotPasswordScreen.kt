package com.example.sensimate.ui.startupscreens.ForgotPassword

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.InitialStartPage.MySensimateLogo
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.startupscreens.components.InitialStartBackground
import com.example.sensimate.ui.startupscreens.components.myButton
import com.example.sensimate.ui.theme.PurpleButtonColor

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    ForgotPassword(rememberNavController(), viewModel())
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForgotPassword(
    navController: NavController,
    startProfileViewModel: StartProfileViewModel
) {
    InitialStartBackground()

    val state = startProfileViewModel._uiState.collectAsState()

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

                Spacer(modifier = Modifier.size(150.dp))


                MyTextField(
                    text = state.value.mail.value,
                    textSize = 15,
                    onValueChange = { startProfileViewModel.changeMail(it)},
                    placeHolder = stringResource(id = R.string.enterMail),
                    width = 300,
                    height = 51,
                    KeyboardType.Email,
                    visualTransformation = VisualTransformation.None,
                    Color.DarkGray,
                    Color.White,
                    Color.Gray
                )

                Spacer(modifier = Modifier.size(25.dp))

                val context = LocalContext.current
                myButton(color = Color.White,
                    title = stringResource(id = R.string.sendRecovery),
                    PurpleButtonColor,
                    onClick = { Database.forgotPassword(email = state.value.mail.value, context, showLoading) }
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        com.example.sensimate.ui.InitialStartPage.showLoading(showLoading)
    }
}
