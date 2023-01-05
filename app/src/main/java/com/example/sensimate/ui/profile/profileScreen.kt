package com.example.sensimate.ui.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.*
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import java.util.Calendar
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController) {
    val scope = rememberCoroutineScope()

    val showDialog = remember {
        mutableStateOf(false)
    }

    val yearBorn = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val dayBorn = remember { mutableStateOf("") }
    val monthBorn = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {

        if (!getBooleanFromLocalStorage("isGuest", context = context)) {
            scope.launch {
                val profile = profile()

                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                age.value = (currentYear - profile.yearBorn.toInt()).toString()

                dayBorn.value = profile.dayBorn
                yearBorn.value = profile.yearBorn
                monthBorn.value = profile.monthBorn
                postalCode.value = profile.postalCode
                gender.value = profile.gender
            }
        } else {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            yearBorn.value = getStringFromLocalStorage("yearBorn", context)

            age.value = (currentYear - yearBorn.value.toInt()).toString()
            dayBorn.value = getStringFromLocalStorage("dayBorn", context)
            monthBorn.value = getStringFromLocalStorage("monthBorn", context)
            postalCode.value = getStringFromLocalStorage("postalCode", context)
            gender.value = getStringFromLocalStorage("gender", context)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(83, 58, 134, 255),
                    0.7f to Color(22, 26, 30)
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MyDialog(navController = navController, showDialog)
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            contentPadding = PaddingValues(bottom = 80.dp, top = 20.dp)
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
                val context = LocalContext.current
                LogoutButton(onClick = {
                    Database.signOut(
                        context = context
                    )
                    navController.popBackStack()

                    navController.navigate(Screen.Login.route)
                })
            }

            // val age = Calendar.getInstance().get(Calendar.YEAR) - ((Database.fetchProfile()?.yearBorn
            //    ?: 0))

            /*
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val age = Database.fetchProfile()?.let { currentYear - it.yearBorn }

            val profile = async { Database.fetchProfile() }.await()
            val age = profile?.let { currentYear - it.yearBorn }
             */

            item { InfoAboutUser(desc = "Age", info = age.value) }
            item { InfoAboutUser(desc = "Year Born", info = yearBorn.value) }
            item { InfoAboutUser(desc = "Day Born", info = dayBorn.value.toString()) }
            item { InfoAboutUser(desc = "Month Born", info = monthBorn.value.toString()) }
            item { InfoAboutUser(desc = "Postal Code", info = postalCode.value.toString()) }
            item { InfoAboutUser(desc = "Gender", info = gender.value.toString()) }

            item { DeleteUserProfileButton(showDialog = showDialog) }
        }
    }
}

private suspend fun profile(): Profile {
    val profile = Database.fetchProfile()
    return Profile(
        yearBorn = profile?.yearBorn.toString(),
        dayBorn = profile?.dayBorn.toString(),
        monthBorn = profile?.monthBorn.toString(),
        gender = profile?.gender.toString(),
        postalCode = profile?.postalCode.toString()
    )
}


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
            text = "Log out",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

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
            text = "Edit",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

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

@Composable
private fun ProfileName() {
    Text(
        text = "Hans Jensen",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        color = Color.White,
        modifier = Modifier.padding(bottom = 5.dp)
    )
}

@Composable
private fun ProfileMail() {
    Text(
        //text = auth.currentUser?.email.toString() ?: "Guest profile",

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

@Composable
fun DeleteUserProfileButton(showDialog: MutableState<Boolean>) {
    Button(
        onClick = {
            showDialog.value = true
        },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(255, 0, 0)),
        modifier = Modifier
            .height(40.dp)
            .padding()
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
private fun MyDialog(
    navController: NavController = rememberNavController(),
    showDialog: MutableState<Boolean> = mutableStateOf(true)
) {
    val context = LocalContext.current

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