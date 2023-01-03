package com.example.sensimate.data

import android.icu.text.CaseMap.Title
import android.media.Image

data class Event(
    var title: String = "",
    var description: String = "",
    var allegerns: String = "",
    var location: String = "",
    var timeOfEvent: String = "",
)
