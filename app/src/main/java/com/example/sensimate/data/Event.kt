package com.example.sensimate.data

import android.icu.text.CaseMap.Title
import android.media.Image

data class Event(
    var title: String = "",
    var description: String = "",
    var allergens: String = "",
    var location: String = "",
    var timeOfEvent: String = "",
    var day: String = "",
    var month: String = "",
    var year: String = "",
    var surveyCode: Int = 0
)
