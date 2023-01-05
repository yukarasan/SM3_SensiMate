package com.example.sensimate.ui.profile.editProfile

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.model.manropeFamily

@Composable
fun EditPasswordScreen(navController: NavController) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showEmptyFieldAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(83, 58, 134, 255),
                    0.7f to Color(22, 26, 30)
                )
            )
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            CheckBox(onClick = {
                if (currentPassword.isNotEmpty() && newPassword.isNotEmpty()) {
                    Database.updatePassword(
                        currentPassword = currentPassword,
                        newPassword = newPassword,
                        context = context
                    )
                    navController.popBackStack()
                } else {
                    // At least one of the text fields is empty
                    showEmptyFieldAlert = true
                }
            })
        }

        if (showEmptyFieldAlert) {
            AlertDialog(
                onDismissRequest = { showEmptyFieldAlert = false },
                text = {
                    Text(
                        "Please provide both your current and new password in " +
                                "their respective fields."
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

        CustomPasswordField(
            text = currentPassword,
            description = "Current password",
            placeholder = "Enter your current password here",
            onValueChange = { currentPassword = it }
        )
        CustomPasswordField(
            text = newPassword,
            description = "New password",
            placeholder = "Enter your new password here",
            onValueChange = { newPassword = it }
        )
        Text(
            text = "To keep your account secure, you can change your password here. Make sure " +
                    " that your password is long enough",
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )
    }
}


// TODO: Give the function to show and hide the password.
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
        var isPasswordVisible = remember {
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
                        painter = painterResource(id = R.drawable.eyeoff),
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