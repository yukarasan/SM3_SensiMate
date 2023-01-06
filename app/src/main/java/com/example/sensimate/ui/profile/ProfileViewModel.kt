package com.example.sensimate.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.data.getStringFromLocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel : ViewModel() {
    // UI state
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        initializeProfile()
    }

    private fun initializeProfile() {
        _uiState.value = ProfileUiState(
            age = _uiState.value.age,
            yearBorn = _uiState.value.yearBorn,
            monthBorn = _uiState.value.monthBorn,
            dayBorn = _uiState.value.dayBorn,
            postalCode = _uiState.value.postalCode,
            gender = _uiState.value.gender,
            email = "",
            currentPassword = "",
            newPassword = ""
        )
    }

    fun fetchProfileData(context: Context) {
        if (!getBooleanFromLocalStorage(key = "isGuest", context = context)) {
            viewModelScope.launch {
                val profile = profile()

                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                _uiState.value.age = (currentYear - profile.yearBorn.toInt()).toString()

                _uiState.value.dayBorn = profile.dayBorn
                _uiState.value.yearBorn = profile.yearBorn
                _uiState.value.monthBorn = profile.monthBorn
                _uiState.value.postalCode = profile.postalCode
                _uiState.value.gender = profile.gender
            }
        } else {
            viewModelScope.launch {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                _uiState.value.yearBorn = getStringFromLocalStorage("yearBorn", context)

                _uiState.value.age = (currentYear - _uiState.value.yearBorn.toInt()).toString()
                _uiState.value.dayBorn = getStringFromLocalStorage("dayBorn", context)
                _uiState.value.monthBorn = getStringFromLocalStorage("monthBorn", context)
                _uiState.value.postalCode = getStringFromLocalStorage("postalCode", context)
                _uiState.value.gender = getStringFromLocalStorage("gender", context)
            }
        }
    }

    private suspend fun profile(): ProfileUiState {
        val profile = Database.fetchProfile()

        _uiState.value.yearBorn = profile?.yearBorn.toString()
        _uiState.value.monthBorn = profile?.monthBorn.toString()
        _uiState.value.dayBorn = profile?.dayBorn.toString()
        _uiState.value.gender = profile?.gender.toString()
        _uiState.value.postalCode = profile?.postalCode.toString()

        return ProfileUiState(
            yearBorn = _uiState.value.yearBorn,
            dayBorn = _uiState.value.dayBorn,
            monthBorn = _uiState.value.monthBorn,
            gender = _uiState.value.gender,
            postalCode = _uiState.value.postalCode
        )
    }

    fun updatePostalCode(postalCode: String) {
        if (_uiState.value.postalCode == "") {
            // Don't do anything
        } else {
            val fields = mapOf(
                "postalCode" to postalCode,
            )
            viewModelScope.launch {
                Database.updateProfileFields(fields)
            }
        }
    }

    fun updatePostalString(input: String) {
        _uiState.value = _uiState.value.copy(postalCode = input)
    }
}