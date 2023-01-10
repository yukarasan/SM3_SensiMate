package com.example.sensimate.ui.profile.editProfile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.profile.ProfileViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

@Composable
fun EditPostalCodeScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showAlertMessage by remember { mutableStateOf(false) }

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
                    if (profileState.postalCode.length < 4) {
                        showAlertMessage = true
                    } else {
                        navController.popBackStack()
                        profileViewModel.updatePostalCode(profileState.postalCode)
                        Toast.makeText(
                            context,
                            "Successfully updated your postal code",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
        CustomProfileTextField(
            text = profileState.postalCode,
            description = "Postal code",
            placeholder = "Enter your postal code here",
            onValueChange = {
                val pattern = "^[0-9]*\$".toRegex()
                if (it.length <= 4 && pattern.matches(it)) {
                    profileViewModel.updatePostalString(input = it)
                }
            }
        )
        Text(
            text = "To give more insightful information to the company, we would like to now about" +
                    " your postal code. If you would like, you can change it here.",
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )

        if (showAlertMessage) {
            AlertDialog(onDismissRequest = { showAlertMessage = false }, text = {
                Text(
                    "The postal code must consist of 4 numbers"
                )
            }, confirmButton = {
                Button(onClick = {
                    showAlertMessage = false
                }) {
                    Text(text = "OK")
                }
            })
        }
    }
}