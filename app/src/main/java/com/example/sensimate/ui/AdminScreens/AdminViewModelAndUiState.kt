package com.example.sensimate.ui.AdminScreens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import com.example.sensimate.ui.InitialStartPage.showLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AdminUiState(
    var mails: MutableList<String> = mutableListOf(),
    var loaded: MutableState<Boolean> = mutableStateOf(false)
)
/**
 * AdminViewModel is a class that provides the data and logic of the Admin screens,
 * it consists of the state of the UI, and the functions that handle the UI state changes.
 * @author Hussein El-Zein
 */
class AdminViewModel() : ViewModel() {
    val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        getListOfEmails()
    }

    fun getListOfEmails() {
        viewModelScope.launch {
            _uiState.value.mails = Database.getAllEmployeesList()
            _uiState.value.loaded.value = true
        }
    }

    fun createdEmployee(email: String) {
        _uiState.value.mails.add(email)
    }

    fun clickOnDeleteEmployee(email: String, context: Context) {
        _uiState.value.mails.remove(email)
        Database.unemployProfile(context, email)
    }
}
