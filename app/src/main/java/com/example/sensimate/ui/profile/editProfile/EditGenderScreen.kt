package com.example.sensimate.ui.profile.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import com.example.sensimate.ui.profile.ProfileViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * EditGenderScreen is a composable that allows the user to edit their gender and update
 * their profile.
 * @param navController: a NavController object that controls navigation within the app.
 * @param profileViewModel: a ProfileViewModel object that holds the state of the user's profile.
 * @author Yusuf Kara
 */
@Composable
fun EditGenderScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profileState by profileViewModel.uiState.collectAsState()

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
                        if (profileState.gender == "") {
                            // Don't do anything
                        } else {
                            profileViewModel.updateGender(gender = profileState.gender)
                        }
                    }
                )
            }
        }

        DropDownMenu(selectedGender = profileState.gender, profileViewModel = profileViewModel)

        Text(
            text = stringResource(id = R.string.genderDescription),
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )
    }
}

/**
 * DropDownMenu is a composable that allows the user to select their gender from a drop-down menu.
 * This composable have been made by Hussein, but have been modified slightly to fit this screen.
 * @param selectedGender: a String representing the currently selected gender.
 * @param profileViewModel: a ProfileViewModel object that holds the state of the user's
 * profile and updates the selected gender.
 * @author Hussein
 */
@Composable
private fun DropDownMenu(selectedGender: String, profileViewModel: ProfileViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf(
        stringResource(id = R.string.male),
        stringResource(id = R.string.female),
        stringResource(id = R.string.other)
    )
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(start = 40.dp, bottom = 20.dp, top = 20.dp, end = 20.dp)) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {
                profileViewModel.updateSelectedGenderString(input = it)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedBorderColor = Color.White,
                disabledPlaceholderColor = Color.White,
                disabledTextColor = Color.White,
                disabledBorderColor = Color.White
            ),
            enabled = false,
            modifier = Modifier
                .width(150.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(stringResource(id = R.string.gender)) },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    modifier = Modifier.clickable { expanded = !expanded },
                    contentDescription = "",
                    tint = Color.White
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        profileViewModel.updateSelectedGenderString(label)
                        expanded = false
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}