package com.example.sensimate.ui.createEmployee

import ContentColor1Component
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.createEvent.TextFiledTitleText
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.LightColor

@Preview
@Composable
fun createEmployeePreview() {
    CreateEmployeeScreen(rememberNavController())
}

@Composable
fun CreateEmployeeScreen(
    navController: NavController,
    createEmployeeViewModel:
    CreateEmployeeViewModel = viewModel()
) {

    val state = createEmployeeViewModel.uiState.collectAsState()
    val context = LocalContext.current

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
    /*
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Title("Add an Employee", modifier = Modifier.size(500.dp))
            Image(modifier = Modifier.size(300.dp).align(Alignment.CenterVertically), id = R.drawable.emp)
        }
    }

     */

    /*
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column() {
            Image(modifier = Modifier.size(50.dp), id = R.drawable.emp)
            Title("Add an Employee", modifier = Modifier.size(500.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

            }
        }

     */

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {

            EmailTextField(titleText = state.value.email.value, textChange = {
                state.value.email.value = it
            })

            PasswordTextField(titleText = state.value.password.value, textChange = {
                state.value.password.value = it
            })
            Spacer(modifier = Modifier.size(70.dp))
            Button(
                onClick = {
                    //eventviewmodel
                          createEmployeeViewModel.checkIfTextFieldIsEmpty(context = context,
                              navController = navController)

                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                modifier = Modifier.size(240.dp, 50.dp),
                enabled = true

            ) {

                Text(
                    text = "Add Employee",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = manropeFamily
                )
            }
        }
    }
}


@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp, bottom = 8.dp)
            .width(220.dp)
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailTextField(titleText: String, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = titleText,
            onValueChange = { textChange(it) },
            label = {
                Text(
                    text = "Email",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(titleText: String, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = titleText,
            onValueChange = { textChange(it) },
            label = {
                Text(
                    text = "Password",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )
    }
}

@Composable
fun Image(modifier: Modifier = Modifier, id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "",
        modifier = modifier
    )
}



