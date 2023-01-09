package com.example.sensimate.ui.profile

import android.content.Context
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
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val context = LocalContext.current

    if (auth.currentUser != null) {
        profileViewModel.fetchProfileData(context = context)
    }

    /*
    val yearBorn = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val dayBorn = remember { mutableStateOf("") }
    val monthBorn = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }

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
    */



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

            item { InfoAboutUser(desc = "Age", info = profileState.age) }
            item { InfoAboutUser(desc = "Year Born", info = profileState.yearBorn) }
            item { InfoAboutUser(desc = "Day Born", info = profileState.dayBorn) }
            item { InfoAboutUser(desc = "Month Born", info = profileState.monthBorn) }
            item { InfoAboutUser(desc = "Postal Code", info = profileState.postalCode) }
            item { InfoAboutUser(desc = "Gender", info = profileState.gender) }
        }
    }
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