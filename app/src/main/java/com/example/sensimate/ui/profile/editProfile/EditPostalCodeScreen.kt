package com.example.sensimate.ui.profile.editProfile

import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import com.example.sensimate.ui.profile.ProfileViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * This composable function is used to create the EditPostalCodeScreen.
 * The screen allows the user to edit their postal code and updates the state of
 * the profileViewModel
 * @param navController: NavController, is used for navigation between screens.
 * @param profileViewModel: ProfileViewModel = viewModel() is the view model containing
 * the state of the user's profile.
 * @author Yusuf Kara
 */
@Composable
fun EditPostalCodeScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // TODO: Check if postal code matches
    val assetManager = context.assets
    val inputStream = assetManager.open("postalcodes.txt")
    val inputString = inputStream.bufferedReader().use { it.readText() }

    /**
     * It is not necessary to include "showAlertMessage" in the viewModel, since it is only
     * used within this composable.
     * Defining it here, allows it to be easily modified within the composable, but is
     * not accessible from outside the composable.
     * If it is needed by other composable or parts of the app, it would be necessary to
     * include it in a viewModel so that it can be observed and accessed from other locations.
     * @author Yusuf Kara
     */
    var showAlertMessage by remember { mutableStateOf(false) }
    var showWrongPostalCodeAlert by remember { mutableStateOf(false) }

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
                        if (profileState.postalCode.length < 4) {
                            showAlertMessage = true
                        } else if (inputString.contains(profileState.postalCode)) {
                            navController.popBackStack()
                            profileViewModel.updatePostalCode(profileState.postalCode)
                            Toast.makeText(
                                context,
                                context.resources.getString(R.string.successfulUpdateOfPostalCode),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            showWrongPostalCodeAlert = true
                        }
                    }
                )
            }
        }
        CustomPostalCodeTextField(
            text = profileState.postalCode,
            description = stringResource(id = R.string.postalCode),
            placeholder = stringResource(id = R.string.enterPostalCode),
            onValueChange = {
                val pattern = "^[0-9]*\$".toRegex()
                if (it.length <= 4 && pattern.matches(it)) {
                    profileViewModel.updatePostalString(input = it)
                }
            }
        )
        Text(
            text = stringResource(id = R.string.postalCodeDescription),
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )

        if (showAlertMessage) {
            AlertDialog(onDismissRequest = { showAlertMessage = false },
                text = {
                    Text(stringResource(id = R.string.postalCodeAlertMessage))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showAlertMessage = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        }

        if (showWrongPostalCodeAlert) {
            AlertDialog(onDismissRequest = { showWrongPostalCodeAlert = false },
                text = {
                    Text("The postal code you provided is not valid")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showWrongPostalCodeAlert = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        }
    }
}

/**
 * I had to copy the following code from the app components, and modify it to fit this
 * specific scenario, where it would make sense to have the keyboard type be of numbers instead
 * of text.
 * Original composable is: CustomProfileTextField
 * @author Yusuf Kara
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun CustomPostalCodeTextField(
    text: String,
    description: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onClick: (() -> Unit)? = null   // Making the onClick optional
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    if (onClick != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 10.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            onClick = onClick
        ) {
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
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    singleLine = true,
                    enabled = false
                )
                Divider(
                    color = Color.White,
                    thickness = 2.dp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 10.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                Divider(
                    color = Color.White,
                    thickness = 2.dp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}