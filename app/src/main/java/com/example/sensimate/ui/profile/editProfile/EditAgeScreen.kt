package com.example.sensimate.ui.profile.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.sensimate.data.Database.updateProfileFields
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

@Composable
fun EditAgeScreen(navController: NavController) {
    var age by remember { mutableStateOf("") }

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
                    navController.popBackStack()
                    // TODO:
                })
            }
        }
        CustomProfileTextField(
            text = age,
            description = "Age",
            placeholder = "Enter the year that you were born",
            onValueChange = { age = it }
        )
        Text(
            text = "To give more insightful information to the company, we would like to now about" +
                    " your age. If you would like, you can change it here.",
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )
    }
}

private suspend fun updateProfile(
    dayBorn: String,
    gender: String,
    monthBorn: String,
    postalCode: String,
    yearBorn: String
) {
    val fields = mapOf(
        "dayBorn" to dayBorn,
        "gender" to gender,
        "monthBorn" to monthBorn,
        "postalCode" to postalCode,
        "yearBorn" to yearBorn
    )

    updateProfileFields(fields)
}