package com.example.sensimate.ui.profile

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
import com.example.sensimate.data.auth
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.DarkPurple

@Composable
fun EditProfileScreen(navController: NavController) {
    /*
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
     */

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                0.0f to Color(83, 58, 134, 255),
                0.7f to Color(22, 26, 30)
            )
        )
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color(83, 58, 134, 255),
                        0.7f to Color(22, 26, 30)
                    )
                ),
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
                InfoAboutUser(
                    desc = "Edit your age here",
                    onClick = {
                        navController.navigate(Screen.EditAgeScreen.route)
                    }
                )
            }
            item {
                InfoAboutUser(
                    desc = "Edit your e-mail here",
                    onClick = {
                        navController.navigate(Screen.EditEmailScreen.route)
                    }
                )
            }
            item {
                InfoAboutUser(
                    desc = "Edit password here",
                    onClick = {
                        navController.navigate(Screen.EditPasswordScreen.route)
                    }
                )
            }
            item {
                InfoAboutUser(
                    desc = "Edit your gender here",
                    onClick = {
                        navController.navigate(Screen.EditGenderScreen.route)
                    }
                )
            }
            item {
                InfoAboutUser(
                    desc = "Edit your postal code here",
                    onClick = {
                        navController.navigate(Screen.EditPostalScreen.route)
                    }
                )
            }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EmailInfoAboutUser(desc: String, info: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth()
            .height(80.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = DarkPurple,
        border = BorderStroke(2.dp, Color(154, 107, 254)),
        onClick = onClick
    ) {
        Column() {
            Text(
                text = desc,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, top = 5.dp)
            )
            Text(
                text = info,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InfoAboutUser(desc: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth()
            .height(80.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = DarkPurple,
        border = BorderStroke(2.dp, Color(154, 107, 254)),
        onClick = onClick
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
        }
    }
}