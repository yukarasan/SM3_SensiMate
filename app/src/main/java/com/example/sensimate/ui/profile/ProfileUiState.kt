package com.example.sensimate.ui.profile

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
    var isEmployee: Boolean = false
)