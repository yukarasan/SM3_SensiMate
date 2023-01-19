package com.example.sensimate.ui.createEmployee

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.ui.AdminScreens.AdminViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing the UI state of the Create Employee Screen
 * @param password: MutableState<String> representing the password entered by the user, password,
 * is not used now but perhaps for later use of the code, to modify changes with
 * creating a employee.
 * @param email: MutableState<String> representing the email entered by the user
 * @author Sabirin Omar
 */

data class CreateEmployeeUIState(
    var password: MutableState<String> = mutableStateOf(""),
    var email: MutableState<String> = mutableStateOf(""),

    )

/**
 * ViewModel class representing the Create Employee Screen, this viewmodel handle the logic of the
 * Create Employee feature
 * @author Sabirin Omar
 */

class CreateEmployeeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateEmployeeUIState())
    val uiState: StateFlow<CreateEmployeeUIState> = _uiState.asStateFlow()

    /**
     * A function that checks if the text fields are empty and if this is true, it would be
     * displayed on the screen. If there is no text field that is empty it would make the giving
     * email an employee in the database.
     * @author Sabirin Omar
     */

    fun checkIfTextFieldIsEmpty(
        context: Context, navController: NavController,
        showLoading: MutableState<Boolean>,
        successLoggedIn: MutableState<Boolean>, adminViewModel: AdminViewModel
    ) {
        if (uiState.value.email.value == "") {
            Toast.makeText(
                context, context.resources.getString(R.string.emailNotEntered),
                Toast.LENGTH_SHORT
            ).show()

        } //maybe for later use;
        /*
        else if (uiState.value.password.value == "") {
            Toast.makeText(
                context, context.resources.getString(R.string.passwordNotEntered),
                Toast.LENGTH_SHORT
            ).show()

         */
        else {
            Database.employProfile(context = context, email = uiState.value.email.value)
            adminViewModel.createdEmployee(
                email = uiState.value.email.value,
            )
        }
    }
}


