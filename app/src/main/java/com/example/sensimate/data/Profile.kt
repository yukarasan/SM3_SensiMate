package com.example.sensimate.data

/**
 * Profile is a data class that holds the users profile information and is used to
 */
data class Profile(
    var dayBorn: String = "",
    var gender: String = "",
    var monthBorn: String = "",
    var postalCode: String = "",
    var yearBorn: String = "",
    var isEmployee: Boolean = false
)
