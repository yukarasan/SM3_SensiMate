package com.example.sensimate.ui.profile.editProfile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.editProfile.CustomProfileTextField
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.profile.ProfileViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * This composable function is used to create the EditPostalCodeScreen.
 * The screen allows the user to edit their postal code and updates the state of
 * the profileViewModel
 * @param navController: NavController, is used for navigation between screens.
 * @param profileViewModel: ProfileViewModel = viewModel() is the view model containing
 * the state of the user's profile.
 */
@Composable
fun EditPostalCodeScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()
    val context = LocalContext.current

    /**
     * It is not necessary to include "showAlertMessage" in the viewModel, since it is only
     * used within this composable.
     * Defining it here, allows it to be easily modified within the composable, but is
     * not accessible from outside the composable.
     * If it is needed by other composables or parts of the app, it would be necessary to
     * include it in a viewModel so that it can be observed and accessed from other locations.
     * @author Yusuf Kara
     */
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
                    OrangeBackButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                }
                CheckBox(onClick = {
                    if (profileState.postalCode.length < 4) {
                        showAlertMessage = true
                    } else {
                        navController.popBackStack()
                        profileViewModel.updatePostalCode(profileState.postalCode)
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.successfulUpdateOfPostalCode),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
        CustomProfileTextField(
            text = profileState.postalCode,
            description = stringResource(id = R.string.postalCode),
            placeholder = stringResource(id = R.string.enterPostalCode),
            onValueChange = {
                val pattern = "^[0-9]*\$".toRegex()
                if (it.length <= 4 && pattern.matches(it)) {
                    profileViewModel.updatePostalString(input = it)
                }
            }
        )
        Text(
            text = stringResource(id = R.string.postalCodeDescription),
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )

        if (showAlertMessage) {
            AlertDialog(onDismissRequest = { showAlertMessage = false },
                text = {
                    Text(stringResource(id = R.string.postalCodeAlertMessage))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showAlertMessage = false
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}