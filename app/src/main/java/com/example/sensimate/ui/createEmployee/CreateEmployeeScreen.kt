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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.sensimate.ui.AdminScreens.AdminViewModel
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.LightColor
/**
 * This class represents the Create Employee Screen, this screen contains the UI for create employee
 * @param navController: NavController, to handle navigation between screens
 * @param createEmployeeViewModel: CreateEmployeeViewModel, the viewmodel for handling Create Employee
 * @param adminViewModel: AdminViewModel, the viewmodel for handling admin operations.
 * @author Sabirin Omar
 */

@Composable
fun CreateEmployeeScreen(
    navController: NavController,
    createEmployeeViewModel: CreateEmployeeViewModel = viewModel(),
    adminViewModel: AdminViewModel = viewModel()
) {

    val state = createEmployeeViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val showLoading = remember {
        mutableStateOf(false)
    }
    val successLoggedIn = remember {
        mutableStateOf(false)
    }

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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OrangeBackButton(onClick = {
                        navController.popBackStack()
                    })
                }
            }
        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(
                    stringResource(id = R.string.createEmployee),
                    modifier = Modifier
                        .padding(start = 0.dp, top = 10.dp)
                )
            }
        }
        item {

            EmailTextField(titleText = state.value.email.value, textChange = {
                state.value.email.value = it
            })
            //Perhaps for later use, to include also password for the employee user:
            /*
            PasswordTextField(titleText = state.value.password.value, textChange = {
                state.value.password.value = it
            })

             */
            Spacer(modifier = Modifier.size(70.dp))
            Button(
                onClick = {
                    createEmployeeViewModel.checkIfTextFieldIsEmpty(
                        context = context,
                        navController = navController, showLoading = showLoading,
                        successLoggedIn = successLoggedIn, adminViewModel = adminViewModel
                    )
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                modifier = Modifier.size(240.dp, 50.dp),
                enabled = true

            ) {

                Text(
                    text = stringResource(id = R.string.addEmployee),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = manropeFamily
                )
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp)
            ) {
                Image(modifier = Modifier.size(360.dp), id = R.drawable.emp)
            }
        }
    }
}


/**
* This composable displays the title of the CreateEmployeeScreen.
* @author Sabirin Omar
*/

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = Color.White,
        modifier = modifier

    )
}


/**
 * This composable is created to make a textField where the admin user can type in the mail
 * of the employee that is about to be created.
 * @author Sabirin Omar
 */


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
                    text = stringResource(id = R.string.email),
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

/**
 * This composable Image is created to display a image in the composoable CreateEmployeeScreen.
 * @author Sabirin Omar
 */


@Composable
fun Image(modifier: Modifier = Modifier, id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "",
        modifier = modifier
    )
}

/**
 * This composable PasswordTextField is created for later use, so that an Admin can create a
 * password for their employees.
 * @author Sabirin Omar
 */

/*
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
                    text = stringResource(id = R.string.password),
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

 */



