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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.ui.appcomponents.editProfile.CheckBox
import com.example.sensimate.ui.appcomponents.editProfile.CustomTextField
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun EditGenderScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val selectedGender = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(83, 58, 134, 255),
                    0.7f to Color(22, 26, 30)
                )
            )
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            CheckBox(onClick = {
                navController.popBackStack()
                scope.launch {
                    updateProfile(gender = selectedGender.value)
                }
            })
        }

        DropDownMenu(selectedGender = selectedGender)

        Text(
            text = "To give more insightful information to the company, we would like to now about" +
                    " your gender. If you would like, you can change it here.",
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp)
        )
    }
}

private suspend fun updateProfile(gender: String) {
    val fields = mapOf(
        "gender" to gender,
    )

    Database.updateProfile(fields)
}

@Composable
private fun DropDownMenu(selectedGender: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Man", "Woman", "Other")
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(start = 40.dp, bottom = 20.dp, top = 20.dp, end = 20.dp)) {
        OutlinedTextField(
            value = selectedGender.value,
            onValueChange = { selectedGender.value = it },
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
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Gender") },
            trailingIcon = {
                Icon(
                    icon, "",
                    Modifier.clickable { expanded = !expanded }, tint = Color.White
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedGender.value = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}