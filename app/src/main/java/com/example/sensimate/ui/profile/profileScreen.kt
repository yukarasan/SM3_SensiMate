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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.*
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * The ProfileScreen composable displays the user's profile information, including their age,
 * year and day of birth, postal code, and gender.
 * It also includes buttons for navigating to the Edit Profile screen, the Event screen, and for
 * logging out.
 * If the user is a guest, the Edit button will not be displayed.
 * @author Yusuf Kara
 */
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    if (auth.currentUser != null) {
        profileViewModel.fetchProfileData(context = context)
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
            contentPadding = PaddingValues(bottom = 20.dp, top = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OrangeBackButton(onClick = { navController.navigate(Screen.EventScreen.route) })

                    if (!getBooleanFromLocalStorage("isGuest", context)) {
                        EditButton(onClick = { navController.navigate(Screen.EditProfileScreen.route) })
                    }
                }
            }
            item { ImageButton() }
            item { ProfileMail() }
            item {
                if (!getBooleanFromLocalStorage("isGuest", context)) {
                    LogoutButton(
                        onClick = {
                            Database.signOut(context = context)
                            navController.popBackStack()
                            navController.navigate(Screen.Login.route)
                        }
                    )
                } else {
                    DeleteUserProfileButton(showDialog = showDialog)
                }
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Dialog(navController = navController, showDialog = showDialog, context = context)
                }
            }
            item { InfoAboutUser(desc = stringResource(id = R.string.age), info = profileState.age) }
            item { InfoAboutUser(desc = stringResource(id = R.string.yearBorn), info = profileState.yearBorn) }
            item { InfoAboutUser(desc = stringResource(id = R.string.dayBorn), info = profileState.dayBorn) }
            item { InfoAboutUser(desc = stringResource(id = R.string.monthBorn), info = profileState.monthBorn) }
            item { InfoAboutUser(desc = stringResource(id = R.string.postalCode), info = profileState.postalCode) }
            item { InfoAboutUser(desc = stringResource(id = R.string.gender), info = profileState.gender) }
        }
    }
}

/**
 * The LogoutButton composable displays a button that, when clicked, triggers the provided
 * onClick function.
 * @param onClick the function that is called when the button is clicked
 * @author Yusuf Kara
 */
@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(239, 112, 103)),
        modifier = Modifier
            .height(40.dp)
            .padding()
    ) {
        Text(
            text = stringResource(id = R.string.logOut),
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
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
 * The EditButton composable displays a button that, when clicked, triggers the provided
 * onClick function.
 * @param onClick the function that is called when the button is clicked
 * @author Yusuf Kara
 */
@Composable
private fun EditButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100),
        border = BorderStroke(3.dp, Color(199, 242, 219)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(239, 112, 103),
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .padding(end = 20.dp)

    ) {
        Text(
            text = stringResource(id = R.string.edit),
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }

}

/**
 * The ImageButton composable displays a circular image with a clickable area. The image is loaded
 * from a drawable resource, and the clickable area is defined by the Box's clickable modifier.
 * For now the click does nothing. But in future work, one would probably want to implement a
 * function that when the user clicks on the image, asks the user if they would like to upload
 * an image of themselves.
 */
@Composable
private fun ImageButton() {
    val image = painterResource(id = R.drawable.profilepic)

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
        )
    }
}

/**
 * The ProfileMail composable displays the email of the current user. If the user is not logged
 * in or the email is null, the composable will display an empty string.
 * @author Yusuf Kara
 */
@Composable
private fun ProfileMail() {
    Text(
        text = if (auth.currentUser?.email != null) {
            auth.currentUser!!.email.toString()
        } else {
            ""
        },
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp,
        color = Color(199, 242, 219),
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

/**
 * The InfoAboutUser composable displays a card that contains two text fields. The card is used
 * to display the users information such as age, email, postal code and so on.
 * @param desc the text for the description
 * @param info the text for the info
 * @author Yusuf Kara
 */
@Composable
private fun InfoAboutUser(desc: String, info: String) {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth()
            .height(80.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = DarkPurple,
        border = BorderStroke(2.dp, Color(154, 107, 254))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = desc,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = info,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                color = Color.White,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}