package com.example.sensimate.ui.profile

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
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
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.auth
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

@Composable
fun EditProfileScreen(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Dialog(navController = navController, showDialog = showDialog, context = context)
    }

    Box(modifier = Modifier
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
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 80.dp, top = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    DoneButton(onClick = { navController.popBackStack() })
                }
            }
            item { ImageButton() }
            /*
            item {
                CustomProfileTextField(
                    text = "Age",
                    description = "Edit your age here",
                    placeholder = "age",
                    onValueChange = { /* nothing for this instance. */  },
                    onClick = {
                        navController.navigate(Screen.EditAgeScreen.route)
                    }
                )
            }
             */
            item {
                CustomProfileTextField(
                    text = "E-mail",
                    description = "Edit your e-mail here",
                    placeholder = "e-mail",
                    onValueChange = { /* nothing for this instance. */  },
                    onClick = {
                        navController.navigate(Screen.EditEmailScreen.route)
                    }
                )
            }
            item {
                CustomProfileTextField(
                    text = "Password",
                    description = "Edit your password here",
                    placeholder = "password",
                    onValueChange = { /* nothing for this instance. */  },
                    onClick = {
                        navController.navigate(Screen.EditPasswordScreen.route)
                    }
                )
            }
            item {
                CustomProfileTextField(
                    text = "Gender",
                    description = "Edit your gender here",
                    placeholder = "gender",
                    onValueChange = { /* nothing for this instance. */  },
                    onClick = {
                        navController.navigate(Screen.EditGenderScreen.route)
                    }
                )
            }
            item {
                CustomProfileTextField(
                    text = "Postal Code",
                    description = "Edit your postal code here",
                    placeholder = "gender",
                    onValueChange = { /* nothing for this instance. */  },
                    onClick = {
                        navController.navigate(Screen.EditPostalScreen.route)
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { DeleteUserProfileButton(showDialog = showDialog) }
        }
    }
}

@Composable
private fun DoneButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100),
        border = BorderStroke(3.dp, Color(199, 242, 219)),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(199, 242, 219)
        ),
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .padding(end = 20.dp)
    ) {
        Text(
            text = "Done",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
private fun DeleteUserProfileButton(showDialog: MutableState<Boolean>) {
    Button(
        onClick = {
            showDialog.value = true
        },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(255, 0, 0)),
        modifier = Modifier
            .height(40.dp)
    ) {
        Text(
            text = "Delete profile",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
private fun Dialog(
    navController: NavController = rememberNavController(),
    showDialog: MutableState<Boolean> = mutableStateOf(true),
    context: Context
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Are you sure you want to delete your profile?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    navController.navigate(Screen.Login.route)
                    Database.deleteProfile(context = context)
                })
                { Text(text = "Yes") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false })
                { Text(text = "No") }
            },
        )
    }
}

@Composable
private fun ImageButton() {
    val image = painterResource(id = R.drawable.profilepic)
    val cameraIcon = painterResource(id = R.drawable.camera__2_)

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(180.dp)
            .padding(bottom = 5.dp)
            .clickable { /* TODO */ }
    ) {
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .clip(shape = CircleShape)
                .fillMaxSize(1f)
                .blur(4.dp)
                .alpha(0.5f),
        )
        Image(
            painter = cameraIcon,
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        )
    }
}