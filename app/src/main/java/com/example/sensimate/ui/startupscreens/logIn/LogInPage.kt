package com.example.sensimate.ui.InitialStartPage

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
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
import com.example.sensimate.data.*
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.ForgotPassword.StartProfileViewModel
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground
import com.example.sensimate.ui.startupscreens.signUp.buttonWithImage
import com.example.sensimate.ui.startupscreens.signUp.myButton
import com.example.sensimate.ui.theme.FaceBookColor
import com.example.sensimate.ui.theme.PurpleButtonColor
import com.example.sensimate.ui.theme.employeelogin
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun LogInMail(
    navController: NavController,
    startProfileViewModel: StartProfileViewModel
) {
    val context = LocalContext.current

    val state = startProfileViewModel._uiState.collectAsState()

    InitialStartBackground()

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
        MyTextField(
            text = state.value.mail.value,
            textSize = 15,
            onValueChange = { startProfileViewModel.changeMail(it) },
            placeHolder = stringResource(id = R.string.enterMail),
            width = 300,
            height = 51,
            KeyboardType.Email,
            visualTransformation = VisualTransformation.None,
            Color.DarkGray,
            Color.White,
            Color.Gray
        )

        Spacer(modifier = Modifier.size(20.dp))

        MyTextField(
            text = state.value.password.value,
            textSize = 15,
            onValueChange = { startProfileViewModel.changePassword(it) },
            placeHolder = stringResource(id = R.string.enterPassword),
            width = 300,
            height = 51,
            KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            Color.DarkGray,
            Color.White,
            Color.Gray
        )

        Spacer(modifier = Modifier.size(5.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 50.dp)
        ) {
            Text(
                stringResource(id = R.string.forgotPassword),
                color = Color.White,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.ForgotPassword.route
                    )
                }
            )
        }


        Spacer(modifier = Modifier.size(28.dp))


        myButton(color = Color.White,
            title = stringResource(id = R.string.signIn),
            PurpleButtonColor,

            onClick = {
                startProfileViewModel.loginAsMail(
                    context = context,
                    showLoading,
                    successLoggedIn
                )
            }
        )
        Spacer(modifier = Modifier.size(50.dp))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        showLoading(showLoading)
    }

    if (successLoggedIn.value) {
        successLoggedIn.value = false

        startProfileViewModel.goToCorrectScreen(navController, context)

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

fun checkIfUserIsEmployeeOrNot(email: String, navController: NavController, context: Context) {
    val userRef = db.collection("users").document(email)

    // Get the 'isEmployee' field from the document
    userRef.get().addOnSuccessListener { snapshot ->
        val isEmployee = snapshot.getBoolean("isEmployee")
        // Check if the user is an employee
        if (isEmployee == true) {
            // Go to the employee page

            SaveBoolToLocalStorage(
                "isEmployee",
                true,
                context = context
            )

            navController.navigate(Screen.EventScreenEmployee.route)
        } else {
            // Go to normal user page
            navController.navigate(Screen.EventScreen.route)

            SaveBoolToLocalStorage(
                "isLoggedIn",
                true,
                context = context
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LogInMailPreview() {
    LogInMail(rememberNavController(), StartProfileViewModel())
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
                title = stringResource(id = R.string.signIn),
                buttonColor = PurpleButtonColor,
                onClick = {}
            )
        } else {
            MyOtherOption(
                color = Color.White,
                title = stringResource(id = R.string.signIn),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        if (screen == Screen.SignUpWithMail) {
            MyOptionChosen(
                color = Color.White,
                title = stringResource(id = R.string.signUp),
                buttonColor = PurpleButtonColor,
                onClick = {}
            )
        } else {
            MyOtherOption(
                color = Color.White,
                title = stringResource(id = R.string.signUp),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignUpWithMail.route)
                }
            )
        }

        if (screen == Screen.Guest) {
            MyOptionChosen(
                color = Color.White,
                title = stringResource(id = R.string.guest),
                buttonColor = PurpleButtonColor,
                onClick = {}
            )
        } else {
            MyOtherOption(
                color = Color.White,
                title = stringResource(id = R.string.guest),
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