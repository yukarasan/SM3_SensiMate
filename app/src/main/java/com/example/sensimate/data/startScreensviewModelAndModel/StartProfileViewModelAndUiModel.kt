package com.example.sensimate.ui.startupscreens.ForgotPassword

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.AnimBuilder
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.SaveStringToLocalStorage
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileReader

data class StartProfileUiState(
    var mail: MutableState<String> = mutableStateOf(""),
    var password: MutableState<String> = mutableStateOf(""),
    var confirmPassword: MutableState<String> = mutableStateOf(""),
    var gender: MutableState<String> = mutableStateOf(""),
    var yearBorn: MutableState<String> = mutableStateOf(""),
    var monthBorn: MutableState<String> = mutableStateOf(""),
    var dayBorn: MutableState<String> = mutableStateOf(""),
    var postalCode: MutableState<String> = mutableStateOf(""),
)

class StartProfileViewModel() : ViewModel() {
    val _uiState = MutableStateFlow(StartProfileUiState())
    val uiState: StateFlow<StartProfileUiState> = _uiState.asStateFlow()

    fun changeMail(mail: String) {
        _uiState.value.mail.value = mail
    }

    fun changePassword(password: String) {
        _uiState.value.password.value = password
    }

    fun changeConfirmPassword(confirmPassword: String) {
        _uiState.value.confirmPassword.value = confirmPassword
    }

    fun changeGender(gender: String) {
        _uiState.value.gender.value = gender
    }

    fun changeYear(year: String) {
        _uiState.value.yearBorn.value = year
    }

    fun changeMonth(month: String) {
        _uiState.value.monthBorn.value = month
    }

    fun changeDay(day: String) {
        _uiState.value.dayBorn.value = day
    }

    fun changePostalCode(postalCode: String) {
        _uiState.value.postalCode.value = postalCode
    }

    fun loginAsGuest(navController: NavController, context: Context) {

        val assetManager = context.assets
        val inputStream = assetManager.open("postalcodes.txt")

        val inputString = inputStream.bufferedReader().use { it.readText() }

        if (uiState.value.gender.value == "" || uiState.value.postalCode.value.length < 4 || uiState.value.yearBorn.value == "") {
            Toast.makeText(
                context,
                context.resources.getString(R.string.rememberInfoError),
                Toast.LENGTH_SHORT
            ).show()
        } else {

            if (!inputString.contains(uiState.value.postalCode.value)) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.rememberPostal),
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                SaveBoolToLocalStorage(
                    "isLoggedIn",
                    true,
                    context
                )

                SaveBoolToLocalStorage(
                    "isGuest",
                    true,
                    context
                )

                SaveStringToLocalStorage(
                    "postalCode",
                    uiState.value.postalCode.value,
                    context
                )

                SaveStringToLocalStorage(
                    "gender",
                    uiState.value.gender.value,
                    context
                )

                SaveStringToLocalStorage(
                    "yearBorn",
                    uiState.value.yearBorn.value,
                    context
                )

                SaveStringToLocalStorage(
                    "monthBorn",
                    uiState.value.monthBorn.value,
                    context
                )

                SaveStringToLocalStorage(
                    "dayBorn",
                    uiState.value.dayBorn.value,
                    context
                )

                Database.loginAnonymously(context)
                navController.popBackStack()
                navController.navigate(Screen.EventScreen.route)
            }
        }
    }

    fun loginAsMail(
        context: Context,
        showLoading: MutableState<Boolean>,
        successLoggedIn: MutableState<Boolean>
    ) {
        if (uiState.value.mail.value == "" || uiState.value.password.value == "") {
            Toast.makeText(
                context,
                context.resources.getString(R.string.passwordMailError),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Database.logIn(
                email = uiState.value.mail.value,
                password = uiState.value.password.value,
                showLoading,
                context,
                successLoggedIn
            )
        }
    }

    fun signUp(
        context: Context,
        showLoading: MutableState<Boolean>,
        successLoggedIn: MutableState<Boolean>,
        showMessage: MutableState<Boolean>
    ) {

        val assetManager = context.assets
        val inputStream = assetManager.open("postalcodes.txt")

        val inputString = inputStream.bufferedReader().use { it.readText() }

        if (uiState.value.password.value != uiState.value.confirmPassword.value) {
            showMessage.value = true
        } else {
            if (uiState.value.postalCode.value.length < 4) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.rememberPostal),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!inputString.contains(uiState.value.postalCode.value)) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.rememberPostal),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (uiState.value.gender.value == "") {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.rememberGender),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (uiState.value.yearBorn.value == "") {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.rememberBirth),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Database.signUserUp(
                    email = uiState.value.mail.value,
                    password = uiState.value.password.value,
                    context = context,
                    showLoading = showLoading,
                    postalCode = uiState.value.postalCode.value,
                    yearBorn = uiState.value.yearBorn.value,
                    monthBorn = uiState.value.monthBorn.value,
                    dayBorn = uiState.value.dayBorn.value,
                    gender = uiState.value.gender.value,
                    successLoggedIn = successLoggedIn
                )
            }
        }
    }

    fun goToCorrectScreen(navController: NavController, context: Context) {
        viewModelScope.launch {

            Database.getIsEmployee(context)

            if (getBooleanFromLocalStorage("isEmployee", context)) {
                Log.d("er EMPLOYEE", "")
                navController.navigate(Screen.EventScreenEmployee.route)
            } else {
                Log.d("ikke EMPLOYEE", "")
                navController.navigate(Screen.EventScreen.route)
            }
        }

    }
}