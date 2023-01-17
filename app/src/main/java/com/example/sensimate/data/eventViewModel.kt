package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    fun insertEvent(event: Event) {
        _uiState.value.events.add(event)
    }

    fun emptyList() {
        _uiState.value.events.removeAll(uiState.value.events)
    }

    fun getEventById(id: String): Event {
        for (event in _uiState.value.events) {
            if (event.eventId == id) {
                updateUiState(event = event)
                Log.d("eventbyId", uiState.value.event.toString())
                return event
            }
        }
        return Event("NO SUCH EVENT")
    }


    fun updateUiState(event: Event) {
        _uiState.value = _uiState.value.copy(event = event)

    }

    fun setChosenEventId(id: String) {
        _uiState.value.chosenSurveyId = id
    }


    fun updateTitleString(title: String) {
        //_uiState.value.event = _uiState.value.event.copy(title = title)

        updateUiState(event = _uiState.value.event.copy(title = title))
    }

    fun updateDescriptionString(description: String) {
        updateUiState(event = _uiState.value.event.copy(description = description))
    }

    fun updateAllergensString(allergens: String) {
        updateUiState(event = _uiState.value.event.copy(allergens = allergens))
    }

    fun updateLocationString(location: String) {
        updateUiState(event = _uiState.value.event.copy(location = location))
    }

    fun updateSurveyCodeString(surveyCode: String) {
        // updateUiState(event = _uiState.value.event.copy(surveyCode = surveyCode))
        _uiState.value.event.chosenSurveyCode.value = surveyCode
    }

    fun updateSurveyCode(surveyCode: String) {
        updateUiState(event = _uiState.value.event.copy(surveyCode = surveyCode))
    }

    fun updateDateString(day: String, month: String, year: String) {
        updateUiState(event = _uiState.value.event.copy(day = day, month = month, year = year))

    }

    fun updateTime(minute: String, hour: String) {
        updateUiState(event = _uiState.value.event.copy(minute = minute, hour = hour))
    }

    @SuppressLint("SuspiciousIndentation")
    fun checkIfTextfieldIsEmpty(
        context: Context, navController: NavController
    ) {
        if (uiState.value.event.title == "") {
            Toast.makeText(context, "Title was not entered", Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.description == "") {
            Toast.makeText(context, "Description was not entered", Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.location == "") {
            Toast.makeText(context, "Location was not entered", Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.year == "" || uiState.value.event.month == ""
            || uiState.value.event.day == ""
        ) {
            Toast.makeText(context, "Date was not entered", Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.minute == "" || uiState.value.event.hour == "") {
            Toast.makeText(context, "Time was not entered", Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.allergens == "") {
            Toast.makeText(context, "Allergens was not entered", Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.surveyCode == "") {
            Toast.makeText(context, "SurveyCode was not entered", Toast.LENGTH_SHORT).show()
        } else{
            updateEvent()
            navController.navigate(Screen.EventScreenEmployee.route)
        }


    }

    fun updateEvent() {
        val event = hashMapOf(
            "title" to uiState.value.event.title,
            "description" to uiState.value.event.description,
            "allergens" to uiState.value.event.allergens,
            "location" to uiState.value.event.location,
            "surveyCode" to uiState.value.event.surveyCode,
            "day" to uiState.value.event.day,
            "month" to uiState.value.event.month,
            "year" to uiState.value.event.year,
            "hour" to uiState.value.event.hour,
            "minute" to uiState.value.event.minute,
            "eventId" to uiState.value.event.eventId
        )
        Log.d("event", event.toString())
        Database.UpdateEvent(event, uiState.value.event.eventId)
    }
}
