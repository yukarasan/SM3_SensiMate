package com.example.sensimate.data

import androidx.compose.runtime.MutableState

data class Event(
    var title: String = "",
    var description: String = "",
    var surveyCode: String = "",
    var eventId: String = "",
    var location: String = "",
    var timeOfEvent: String = "",
    var adresss: String = "",
    var allergens: String = "",
    var day: String = "",
    var month: String = "",
    var year: String = "",
   // var hour: String = "", //TODO  skal erstatte timeOfEvent
   // var minute: String ="",//TODO  skal erstatte timeOfEvent
)

data class EventUiState(
    var events: MutableList<Event> = emptyList<Event>().toMutableList(),
    var chosenSurveyId: String = ""
)