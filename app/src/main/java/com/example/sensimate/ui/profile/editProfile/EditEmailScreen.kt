package com.example.sensimate.ui.profile.editProfile

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.data.auth
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.profile.ProfileViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditEmailScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()

    var showEmptyFieldAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // var codeExecuted by remember { mutableStateOf(false) }

    if (auth.currentUser != null /* && !codeExecuted */) {
        profileViewModel.fetchProfileData(context = context)
        // codeExecuted = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    OrangeBackButton(onClick = {
                        navController.popBackStack()
                    })
                }
                CheckBox(onClick = {
                    if (
                        profileState.currentPassword.isNotEmpty()
                        &&
                        profileState.email.isNotEmpty()
                    ) {
                        Database.updateEmail(
                            postalCode = profileState.postalCode,
                            yearBorn = profileState.yearBorn,
                            monthBorn = profileState.monthBorn,
                            dayBorn = profileState.dayBorn,
                            gender = profileState.gender,
                            currentPassword = profileState.currentPassword,
                            newEmail = profileState.email,
                            context = context
                        )

                        // codeExecuted = false

                        navController.popBackStack()
                    } else {
                        // At least one of the text fields is empty
                        showEmptyFieldAlert = true
                    }
                })
            }
        }
        CustomPasswordField(
            text = profileState.currentPassword,
            description = "Current password",
            placeholder = "Enter your current password here",
            onValueChange = {
                profileViewModel.updateCurrentPasswordString(input = it)
            }
        )
        CustomProfileTextField(
            text = profileState.email,
            description = "E-mail",
            placeholder = "Enter your new e-mail here",
            onValueChange = {
                profileViewModel.updateEmailString(input = it)
            }
        )
        Text(
            text = "To give you the best experience, we recommend that your email is up to date. " +
                    "You can change it here. For this we will need you to confirm the change by " +
                    "entering your password as well",
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )

        if (showEmptyFieldAlert) {
            AlertDialog(
                onDismissRequest = { showEmptyFieldAlert = false },
                text = {
                    Text(
                        "Please provide both your current password and your desired e-mail " +
                                "in their respective fields."
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        showEmptyFieldAlert = false
                    }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun CustomPasswordField(
    text: String,
    description: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, top = 10.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        val isPasswordVisible = remember {
            mutableStateOf(false)
        }

        Column() {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                text = description,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isPasswordVisible.value) {
                    Log.d("Password is hidden", isPasswordVisible.toString())
                    BasicTextField(
                        value = text,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = manropeFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        ),
                        decorationBox = { innerTextField ->
                            Row() {
                                if (text.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontFamily = manropeFamily,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Start
                                    )
                                }
                                innerTextField()
                            }
                        },
                        cursorBrush = SolidColor(Color(154, 107, 254)),
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 2.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        maxLines = 1,
                        singleLine = true
                    )
                } else {
                    Log.d("Password is not hidden", isPasswordVisible.toString())
                    BasicTextField(
                        value = text,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = manropeFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        ),
                        decorationBox = { innerTextField ->
                            Row() {
                                if (text.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontFamily = manropeFamily,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Start
                                    )
                                }
                                innerTextField()
                            }
                        },
                        cursorBrush = SolidColor(Color(154, 107, 254)),
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 2.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        maxLines = 1,
                        singleLine = true
                    )
                }
                IconButton(onClick = {
                    if (isPasswordVisible.value == false) {
                        isPasswordVisible.value = true
                    } else if (isPasswordVisible.value == true) {
                        isPasswordVisible.value = false
                    }
                }) {
                    Image(
                        painter = painterResource(id = com.example.sensimate.R.drawable.eyeoff),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Divider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}