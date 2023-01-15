package com.example.sensimate.ui.profile

/**
 * The ProfileUiState data class represents the state of the profile information being displayed
 * in the user interface.
 * It contains fields for storing the user's age, date of birth, postal code, gender, email,
 * current password, new password, and employment status.
 * @author Yusuf Kara
 */
data class ProfileUiState(
    var age: String = "",
    var yearBorn: String = "",
    var monthBorn: String = "",
    var dayBorn: String = "",
    var postalCode: String = "",
    var gender: String = "",
    var email: String = "",
    var currentPassword: String = "",
    var newPassword: String = "",
    var isEmployee: Boolean = false,
    var isAdmin: Boolean = false
)