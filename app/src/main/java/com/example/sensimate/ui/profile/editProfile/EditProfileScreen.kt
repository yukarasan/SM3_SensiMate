package com.example.sensimate.ui.profile.editProfile

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * The EditProfileScreen composable displays a screen that allows the user to edit their email,
 * password, and gender.
 * The screen includes a Dialog composable that is used to confirm the user's actions when he/she
 * wants to delete their account.
 * @param navController the navigation controller for navigating between screens
 * @author Yusuf Kara
 */
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

    Box(
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
            item {
                CustomProfileTextField(
                    text = stringResource(id = R.string.email),
                    description = stringResource(id = R.string.editEmail),
                    placeholder = stringResource(id = R.string.email),
                    onValueChange = { /* nothing for this instance. */ },
                    onClick = {
                        navController.navigate(Screen.EditEmailScreen.route)
                    }
                )
            }
            item {
                CustomProfileTextField(
                    text = stringResource(id = R.string.password),
                    description = stringResource(id = R.string.editPassword),
                    placeholder = stringResource(id = R.string.password),
                    onValueChange = { /* nothing for this instance. */ },
                    onClick = {
                        navController.navigate(Screen.EditPasswordScreen.route)
                    }
                )
            }
            item {
                CustomProfileTextField(
                    text = stringResource(id = R.string.gender),
                    description = stringResource(id = R.string.editGender),
                    placeholder = stringResource(id = R.string.gender),
                    onValueChange = { /* nothing for this instance. */ },
                    onClick = {
                        navController.navigate(Screen.EditGenderScreen.route)
                    }
                )
            }
            item {
                CustomProfileTextField(
                    text = stringResource(id = R.string.postalCode),
                    description = stringResource(id = R.string.editPostalCode),
                    placeholder = stringResource(id = R.string.postalCode),
                    onValueChange = { /* nothing for this instance. */ },
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

/**
 * The DoneButton composable displays a button that, when clicked, triggers the provided
 * onClick function.
 * @param onClick the function that is called when the button is clicked
 */
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
            text = stringResource(id = R.string.doneButton),
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

/**
 * The DeleteUserProfileButton composable displays a button that, when clicked, sets the value
 * of the showDialog state variable to true.
 * @param showDialog the state variable that is used to control the visibility of the
 * delete confirmation dialog
 * @author Yusuf Kara
 */
@Composable
private fun DeleteUserProfileButton(showDialog: MutableState<Boolean>) {
    Button(
        onClick = {
            showDialog.value = true
        },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(184, 58, 58, 255)),
        modifier = Modifier
            .height(40.dp)
    ) {
        Text(
            text = stringResource(id = R.string.deleteProfile),
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

/**
 * The Dialog composable displays an AlertDialog that prompts the user to confirm the deletion
 * of their profile.
 * The dialog includes a "Yes" button that, when clicked, deletes the user's profile and
 * navigates to the login screen, and a "No" button that dismisses the dialog.
 * The visibility of the dialog is controlled by the showDialog state variable.
 * @param navController the navigation controller for navigating between screens
 * @param showDialog the state variable that is used to control the visibility of the
 * delete confirmation dialog
 * @param context the context in which the dialog is displayed
 * @author Yusuf Kara
 */
@Composable
private fun Dialog(
    navController: NavController = rememberNavController(),
    showDialog: MutableState<Boolean> = mutableStateOf(true),
    context: Context
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = stringResource(id = R.string.deleteProfileConfirmation)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    navController.navigate(Screen.Login.route)
                    Database.deleteProfile(context = context)
                })
                { Text(text = stringResource(id = R.string.yes)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false })
                { Text(text = stringResource(id = R.string.no)) }
            },
        )
    }
}

/**
 * The InfoAboutUser composable displays a card that contains two text fields. The card is used
 * to display the users information such as age, email, postal code and so on.
 * @param desc the text for the description
 * @param info the text for the info
 * @author Yusuf Kara
 */
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