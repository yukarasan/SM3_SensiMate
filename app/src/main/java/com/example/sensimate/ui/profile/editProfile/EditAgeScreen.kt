package com.example.sensimate.ui.profile.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * EditAgeScreen is a composable that allows the user to edit their age and update their profile.
 * For now this composable is not being used, as we decided further on in the development, that it
 * would not be necessary. But if one wish to include this, they can simply call the composable
 * from the profile screen and edit profile screen.
 * @param navController: a NavController object that controls navigation within the app.
 * @author Yusuf Kara
 */
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
                    OrangeBackButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                }
                CheckBox(
                    onClick = {
                        navController.popBackStack()
                        // For now no update of age is happening
                    }
                )
            }
        }
        CustomProfileTextField(
            text = age,
            description = stringResource(id = R.string.age),
            placeholder = stringResource(id = R.string.enterAge),
            onValueChange = { age = it }
        )
        Text(
            text = stringResource(id = R.string.ageDescription),
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )
    }
}

/*
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
 */