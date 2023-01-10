package com.example.sensimate.ui.profile

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import com.example.sensimate.data.getBooleanFromLocalStorage
import com.example.sensimate.data.getStringFromLocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Year
import java.util.*

/**
 * This class represents the view model for a profile screen.
 * The class uses the MutableStateFlow and StateFlow classes to maintain the state of the user
 * interface.
 * The class also uses the Coroutines and the viewModelScope to fetch and update the profile
 * data from the database.
 * The class contains two function
 * @function initializeProfile() initialize the profile state.
 * @function fetchProfileData(context: Context) fetch the profile data, whether it's a guest or not.
 * @author Yusuf Kara
 */
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
            currentPassword = _uiState.value.currentPassword,
            newPassword = _uiState.value.newPassword,
            email = _uiState.value.email,
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchProfileData(context: Context) {
        if (!getBooleanFromLocalStorage(key = "isGuest", context = context)) {
            viewModelScope.launch {
                val profile = profile()

                val currentCalendar = Calendar.getInstance()
                val currentYear = currentCalendar.get(Calendar.YEAR)
                val currentMonth = currentCalendar.get(Calendar.MONTH)
                val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)

                val birthCalendar = Calendar.getInstance()
                birthCalendar.set(Calendar.YEAR, profile.yearBorn.toInt())
                birthCalendar.set(Calendar.MONTH, profile.monthBorn.toInt())
                birthCalendar.set(Calendar.DAY_OF_MONTH, profile.dayBorn.toInt())

                val birthYear = birthCalendar.get(Calendar.YEAR)
                val birthMonth = birthCalendar.get(Calendar.MONTH)
                val birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH)

                var age = currentYear - birthYear
                if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                    age--
                }

                if (Year.isLeap(currentYear.toLong()) && (birthMonth > 2 || (birthMonth == 2 && birthDay == 29))) {
                    age++
                }
                if (Year.isLeap(birthYear.toLong()) && birthMonth < 2) {
                    age--
                }

                _uiState.value.age = age.toString()

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

    fun updateGender(gender: String) {
        if (_uiState.value.gender == "") {
            // Don't do anything
        } else {
            viewModelScope.launch {
                val fields = mapOf(
                    "gender" to gender,
                )

                Database.updateProfileFields(fields)
            }
        }
    }

    fun updateSelectedGenderString(input: String) {
        _uiState.value = _uiState.value.copy(gender = input)
    }

    fun updateCurrentPasswordString(input: String) {
        _uiState.value = _uiState.value.copy(currentPassword = input)
    }

    fun updateNewPasswordString(input: String) {
        _uiState.value = _uiState.value.copy(newPassword = input)
    }

    fun updateEmailString(input: String) {
        _uiState.value = _uiState.value.copy(email = input)
    }
}