package com.example.sensimate.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Event(
    var title : MutableState<String> = mutableStateOf(""),
    var description: MutableState<String> = mutableStateOf(""),
    var surveyCode: MutableState<String> = mutableStateOf(""),
    var eventId: MutableState<String> = mutableStateOf(""),
    var location: MutableState<String> = mutableStateOf(""),
    var allergens: MutableState<String> = mutableStateOf(""),
    var day: MutableState<String> = mutableStateOf(""),
    var month: MutableState<String> = mutableStateOf(""),
    var year: MutableState<String> = mutableStateOf(""),
    var hour: MutableState<String> = mutableStateOf(""),
    var minute: MutableState<String> = mutableStateOf(""),
    var chosenSurveyCode: MutableState<String> = mutableStateOf("")
)

data class EventUiState(
    var events: MutableList<Event> = emptyList<Event>().toMutableList(),
    var chosenSurveyId: String = "",
    var event: Event = Event(),

)
