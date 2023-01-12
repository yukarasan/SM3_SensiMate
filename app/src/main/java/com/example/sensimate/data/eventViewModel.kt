package com.example.sensimate.data

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    fun insertEvent(event: Event) {
        _uiState.value.events.add(event)
    }

    fun emptyList(){
        _uiState.value.events.removeAll(uiState.value.events)
    }

    fun getEventById(id: String): Event {
        for (event in _uiState.value.events) {
            if (event.eventId == id) {
                updateUiState(event = event)
                Log.d("eventbyId",uiState.value.event.toString())
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
        // updateUiState(event = _uiState.value.event.copy(surveyCode = SurveyCode))
        _uiState.value.event.chosenSurveyCode.value = surveyCode
    }

    fun updateDateString(day: String, month: String, year: String){
        updateUiState(event = _uiState.value.event.copy(day = day, month = month, year = year))

    }

    fun updateTime(minute : String, hour : String){
        updateUiState(event = _uiState.value.event.copy(minute = minute, hour = hour))
    }

    fun checkIfTextfieldIsEmpty(
        context: android.content.Context,
        title: String,
        description: String,
        location: String,
        year: String,
        month: String,
        day: String,
        allergens: String,
        surveyCode: String
    ) {

        if ((title == "").also { uiState.value.event.title = it.toString() }) {
            Toast.makeText(context, "Title was not entered", Toast.LENGTH_SHORT).show()
        } else if ((description == "").also { uiState.value.event.description = it.toString() }) {
            Toast.makeText(context, "Description was not entered", Toast.LENGTH_SHORT).show()

        } else if ((location == "").also { uiState.value.event.location = it.toString() }) {
            Toast.makeText(context, "Location was not entered", Toast.LENGTH_SHORT).show()

        } else if ((year == "").also { uiState.value.event.year = it.toString() } ||
            (month == "").also { uiState.value.event.month = it.toString() } ||
            (day == "").also { uiState.value.event.day = it.toString() }) {
            Toast.makeText(context, "Date was not entered", Toast.LENGTH_SHORT).show()

        } else if ((allergens == "").also { uiState.value.event.allergens = it.toString() }) {
            Toast.makeText(context, "Allergens was not entered", Toast.LENGTH_SHORT).show()

        } else if ((surveyCode == "").also { uiState.value.event.surveyCode = it.toString() }) {
            Toast.makeText(context, "SurveyCode was not entered", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateEvent(title: String,
                    description: String,
                    location: String,
                    year: String,
                    month: String,
                    day: String,
                    allergens: String,
                    hour : String,
                    minute : String,
                    surveyCode: String,
                    eventId : String){
        val event = hashMapOf(
            "title" to title,
            "description" to description,
            "allergens" to allergens,
            "location" to location,
            "surveyCode" to surveyCode,
            "day" to day,
            "month" to month,
            "year" to year,
            "hour" to hour,
            "minute" to minute,
            "eventId" to eventId
        )
        Log.d("event", event.toString())

        Database.UpdateEvent(event, uiState.value.event.eventId)

    }


}
