package com.example.sensimate.ui.createEmployee

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.data.EventUiState
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class CreateEmployeeUIState(
    var password: MutableState<String> = mutableStateOf(""),
    var email: MutableState<String> = mutableStateOf(""),

)
class CreateEmployeeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CreateEmployeeUIState())
    val uiState: StateFlow<CreateEmployeeUIState> = _uiState.asStateFlow()

    fun checkIfTextFieldIsEmpty(context: Context, navController: NavController) {
        if (uiState.value.email.value == "") {
            Toast.makeText(
                context,
                "Email was not entered, try again",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uiState.value.password.value == "") {
            Toast.makeText(
                context,
                "Password was not entered, try again",
                Toast.LENGTH_SHORT
            ).show()
        } else {
           // Database.createEmployee(uiState.value.email.value,
              //  uiState.value.password.value )
            //insæt i databasen.
            //navigere til en anden screen.
        }
    }
}


