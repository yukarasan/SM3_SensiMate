package com.example.sensimate.ui.Event.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


/**
 * This data class below represents an event. It contains fields for storing the the event's title,
 * description, surveycode, eventId, location, allergens, day, month, year, hour and minute. All
 * those strings has a default value, lastly we have chosenSurveyCode, which is a mutablestate of
 * type string representing the chosen survey code.
 * @author Sabirin Omar
 */


data class Event(
    var title: String = "",
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

/**
 * EventUiState class is used to hold data for the UI state of the Event class.
 * @property events a MutableList of Event objects representing a list of events.
 * @property chosenSurveyId a String representing the id of the chosen survey.
 * @property event an Event object representing the current event being displayed.
 * @author Sabirin Omar
 */


data class EventUiState(
    var events: MutableList<Event> = emptyList<Event>().toMutableList(),
    var chosenSurveyId: String = "",
    var event: Event = Event(),

    )
