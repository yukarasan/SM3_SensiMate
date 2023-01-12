package com.example.sensimate.ui.profile.editProfile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.profile.ProfileViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple


/**
 * The EditPasswordScreen function is used to allow a user to update their password.
 * It has two input parameters: a navController and a profileViewModel.
 * The navController is used to navigate between screens and the profileViewModel is used to update
 * the user's password information.
 * The function contains a number of variables that are used to show different alerts to the user,
 * such as if the new password is not long enough or if a field is empty.
 * @author Yusuf Kara
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditPasswordScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()

    /**
     * It is not necessary to include "showWrongLengthOfPassword" and "showEmptyFieldAlert" in
     * a viewModel, since they are only used within this composable.
     * Defining them here, allows them to be easily modified within the composable, but they are
     * not accessible from outside the composable.
     * If "showWrongLengthOfPassword" and "showEmptyFieldAlert" are needed by other composables or
     * parts of the app, it would be necessary to include them in a viewModel so that they can
     * be observed and accessed from other locations.
     * @author Yusuf Kara
     */
    var showWrongLengthOfPassword by remember { mutableStateOf(false) }
    var showEmptyFieldAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                    OrangeBackButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                }
                CheckBox(
                    onClick = {
                        if (
                            profileState.currentPassword.isNotEmpty()
                            && profileState.newPassword.isNotEmpty()
                        ) {
                            if (profileState.newPassword.length < 8) {
                                showWrongLengthOfPassword = true
                            } else {
                                Database.updatePassword(
                                    currentPassword = profileState.currentPassword,
                                    newPassword = profileState.newPassword,
                                    context = context
                                )
                                navController.popBackStack()
                            }
                        } else {
                            showEmptyFieldAlert = true // At least one of the text fields is empty
                        }
                    }
                )
            }
        }

        if (showEmptyFieldAlert) {
            AlertDialog(
                onDismissRequest = { showEmptyFieldAlert = false },
                text = {
                    Text(stringResource(id = R.string.provideBothPasswordsAlertMessage))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showEmptyFieldAlert = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        }

        if (showWrongLengthOfPassword) {
            AlertDialog(
                onDismissRequest = { showWrongLengthOfPassword = false },
                text = {
                    Text(stringResource(id = R.string.notLongEnoughPasswordAlertMessage))
                },
                confirmButton = {
                    Button(
                        onClick = {
                        showWrongLengthOfPassword = false
                    }
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        }

        CustomPasswordField(
            text = profileState.currentPassword,
            description = stringResource(id = R.string.currentPassword),
            placeholder = stringResource(id = R.string.enterCurrentPassword),
            onValueChange = { profileViewModel.updateCurrentPasswordString(input = it) },
        )
        CustomPasswordField(
            text = profileState.newPassword,
            description = stringResource(id = R.string.newPassword),
            placeholder = stringResource(id = R.string.enterNewPassword),
            onValueChange = { profileViewModel.updateNewPasswordString(input = it) },
        )
        Text(
            text = stringResource(id = R.string.passwordDescription),
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )
    }
}

/**
 * The CustomPasswordField function is used to create a password field with a custom design.
 * It has five input parameters: text, description, placeholder, onValueChange, and modifier.
 * @author Yusuf Kara
 */
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
private fun CustomPasswordField(
    text: String,
    description: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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

        Column {
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
                            Row {
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
                        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        ),
                        maxLines = 1,
                        singleLine = true
                    )
                } else {
                    BasicTextField(
                        value = text,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = manropeFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                        ),
                        decorationBox = { innerTextField ->
                            Row {
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
                        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        ),
                        maxLines = 1,
                        singleLine = true,
                    )
                }
                IconButton(onClick = {
                    if (!isPasswordVisible.value) {
                        isPasswordVisible.value = true
                    } else if (isPasswordVisible.value) {
                        isPasswordVisible.value = false
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.eyeoff),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Divider(
                color = Color.White, thickness = 2.dp, modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}

