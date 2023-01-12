package com.example.sensimate.data

data class Survey(
    var dayBorn: String = "",
    var gender: String = "",
    var monthBorn: String = "",
    var postalCode: String = "",
    var yearBorn: String = "",
    var isEmployee: Boolean = false,
    var answer: List<String>

)


