package com.example.sensimate.ui.createEmployee

import android.content.Context
import android.provider.ContactsContract.Data
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.EventUiState
import com.example.sensimate.ui.AdminScreens.AdminViewModel
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class CreateEmployeeUIState(
    var password: MutableState<String> = mutableStateOf(""),
    var email: MutableState<String> = mutableStateOf(""),

    )

class CreateEmployeeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateEmployeeUIState())
    val uiState: StateFlow<CreateEmployeeUIState> = _uiState.asStateFlow()

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
            /*Database.signUserUp(email = uiState.value.email.value, password = uiState.value.password.value,
                context = context, showLoading = showLoading, postalCode = "null", yearBorn = "null",
                monthBorn = "null", gender = "null", dayBorn = "null", successLoggedIn = successLoggedIn,
                isEmployee = true
            )
             */

            Database.employProfile(context = context, email = uiState.value.email.value)

            adminViewModel.createdEmployee(
                email = uiState.value.email.value,
            )
        }
    }
}


