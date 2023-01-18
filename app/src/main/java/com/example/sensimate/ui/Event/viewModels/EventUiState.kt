package com.example.sensimate.ui.Event.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Event(
    var title : String = "",
    var description: String = "",
    var surveyCode: String = "",
    var eventId: String = "",
    var location: String = "",
    var allergens: String = "",
    var day: String = "",
    var month: String = "",
    var year: String = "",
    var hour: String = "",
    var minute: String = "",
    var chosenSurveyCode: MutableState<String> = mutableStateOf(""),
)

data class EventUiState(
    var events: MutableList<Event> = emptyList<Event>().toMutableList(),
    var chosenSurveyId: String = "",
    var event: Event = Event(),

    )
