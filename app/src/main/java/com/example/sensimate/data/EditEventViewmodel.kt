package com.example.sensimate.data


import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class EditEventViewmodel : ViewModel() {
    private val _uiState = MutableStateFlow(Event())
    val uiState: StateFlow<Event> = _uiState.asStateFlow()

    fun createHashMapforEvent(
        titleText: String,
        descriptionText: String,
        allergensText: String,
        locationText: String,
        surveyCodeText: String,
        day: String,
        month: String,
        year: String,
        min : String,
        hour : String,
        eventId: String,
    ): HashMap<String, String> {

        uiState.value.title = titleText
        uiState.value.description = descriptionText
        uiState.value.allergens = allergensText
        uiState.value.location = locationText
        uiState.value.surveyCode = surveyCodeText
        uiState.value.month = month
        uiState.value.year = year
        uiState.value.day = day
        uiState.value.eventId = eventId
        uiState.value.minute = min
        uiState.value.hour = hour

        val event = hashMapOf(
            "title" to titleText,
            "description" to descriptionText,
            "allergens" to allergensText,
            "location" to locationText,
            "surveyCode" to surveyCodeText,
            "day" to day,
            "month" to month,
            "year" to year,
            "minute" to min,
            "hour" to hour,
            "eventId" to eventId
        )
        return event
    }

    fun checkIfTextfieldIsEmpty(
        context : android.content.Context,
        title: String,
        description: String,
        location: String,
        year: String,
        month: String,
        day: String,
        allergens: String,
        surveyCode: String
    ) {

        if ((title == "").also { uiState.value.title = it.toString() }) {
            Toast.makeText(context, "Title was not entered", Toast.LENGTH_SHORT).show()
        }
        else if ((description == "").also { uiState.value.description = it.toString() }) {
            Toast.makeText(context, "Description was not entered", Toast.LENGTH_SHORT).show()

        } else if ((location == "").also { uiState.value.description = it.toString() }) {
            Toast.makeText(context, "Location was not entered", Toast.LENGTH_SHORT).show()

        } else if ((year == "").also { uiState.value.year = it.toString() } ||
            (month == "").also { uiState.value.month = it.toString() } ||
            (day == "").also { uiState.value.day = it.toString() }) {
            Toast.makeText(context, "Date was not entered", Toast.LENGTH_SHORT).show()

        } else if ((allergens == "").also { uiState.value.allergens = it.toString() }) {
            Toast.makeText(context, "Allergens was not entered", Toast.LENGTH_SHORT).show()

        } else if ((surveyCode == "").also { uiState.value.surveyCode = it.toString() }) {
            Toast.makeText(context, "SurveyCode was not entered", Toast.LENGTH_SHORT).show()
        }
    }
}


