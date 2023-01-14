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

    /**
     * A function that initializes the profile.
     * This function sets the value of _uiState variable to a new instance of ProfileUiState
     * class with values of the existing _uiState variable fields.
     * @author Yusuf Kara
     */
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

    /**
     * A function that retrieves profile data.
     * The function first checks whether the user is a guest or not by calling
     * 'getBooleanFromLocalStorage' function with the key 'isGuest'. If the user is not a guest,
     * the function uses coroutines to retrieve the profile data and calculates the age of the user.
     * The data is then stored in the _uiState variable. If the user is a guest, the function uses
     * coroutines to retrieve the data from local storage and store it in the _uiState variable.
     * @author Yusuf Kara
     */
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

    /**
     * The profile() function is a private, suspended function used to retrieve the user's
     * profile information from the database and update the ProfileUiState object with the
     * retrieved information.
     * It calls the Database.fetchProfile() function to get the user's profile information and
     * updates the fields of the _uiState.value.
     * @return ProfileUiState object with the current user's profile information
     * @author Yusuf Kara
     */
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

    /**
     * A function that updates the postal code of the user's profile.
     * @param postalCode : The new postal code value.
     * The function checks if the current postal code value is an empty string, If true it will
     * not do anything.
     * If the current postal code value is not an empty string, it creates a map of fields to update,
     * the map that contains only one key-value: "postalCode" to the new postal code value.
     * Then it uses coroutine to launch the updateProfileFields function in the Database class
     * with the fields map as an argument.
     */
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

    /**
     * Updates the postal code stored in the UI state.
     */
    fun updatePostalString(input: String) {
        _uiState.value = _uiState.value.copy(postalCode = input)
    }

    /**
     * * A function that updates the gender of the user's profile.
     * @param gender : The new gender value.
     * The function checks if the current gender value is an empty string, If true it will
     * not do anything.
     * If the current gender value is not an empty string, it creates a map of fields to update,
     * the map that contains only one key-value: "gender" to the new gender value.
     * Then it uses coroutine to launch the updateProfileFields function in the Database class
     * with the fields map as an argument.
     */
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

    /**
     * Updates the gender stored in the UI state.
     */
    fun updateSelectedGenderString(input: String) {
        _uiState.value = _uiState.value.copy(gender = input)
    }

    /**
     * Updates the password stored in the UI state.
     */
    fun updateCurrentPasswordString(input: String) {
        _uiState.value = _uiState.value.copy(currentPassword = input)
    }

    /**
     * Updates the new password stored in the UI state.
     */
    fun updateNewPasswordString(input: String) {
        _uiState.value = _uiState.value.copy(newPassword = input)
    }

    /**
     * Updates the e-mail stored in the UI state.
     */
    fun updateEmailString(input: String) {
        _uiState.value = _uiState.value.copy(email = input)
    }

    // TODO: Remove logic from screens to methods in here. Example, check if strings match
}