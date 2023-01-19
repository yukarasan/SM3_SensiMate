package com.example.sensimate.data

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.ui.Event.viewModels.Event
import com.example.sensimate.ui.Event.viewModels.EventUiState
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * EventViewModel class is a ViewModel class that holds the state of the events.It uses
 * MutableStateFlow to manage the state of the events and provides the state through uiState.
 * It then provides the functions below
 * @author Sabirin Omar
 */

class EventViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    /**
     * This function below insertEvent, takes as argument an Event object, and then inserts the
     * event into the events list.
     * @author Sabirin Omar
     */

    fun insertEvent(event: Event) {
        _uiState.value.events.add(event)
    }

    /**
     * This function emptyList, is only used to empty the list of the events.
     * @author Sabirin Omar
     */

    fun emptyList() {
        _uiState.value.events.removeAll(uiState.value.events)
    }

    /**
     * This function getEventByID takes as argument id (eventID) which is a string and returns a
     * whole event. The function will then update the state with the event and return it.
     * @author Sabirin Omar
     */

    fun getEventById(id: String): Event {
        for (event in _uiState.value.events) {
            if (event.eventId == id) {
                updateUiState(event = event)
                return event
            }
        }
        return Event("NO SUCH EVENT")
    }
    /**
     * This function below UpdateUIState, updates the state of the UI with the provided event.
     * This function is used to update the differents states of the UIState below.
     * @author Sabirin Omar
     */

    fun updateUiState(event: Event) {
        _uiState.value = _uiState.value.copy(event = event)

    }

    fun setChosenEventId(id: String) {
        _uiState.value.chosenSurveyId = id
    }


    fun updateTitleString(title: String) {

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

    /**
     * This function below checks if the text field in the IU state are empty. If they are empty,
     * a toast message is displayed indicating which field is empty. But if the fields are filled
     * the event is updated and the employee is navigated to the EventScreenEmployee.
     * @author Sabirin Omar
     */

    @SuppressLint("SuspiciousIndentation")
    fun checkIfTextfieldIsEmpty(
        context: Context, navController: NavController
    ) {
        if (uiState.value.event.title == "") {
            Toast.makeText(context, context.resources.getString(R.string.titleNotEntered),
                Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.description == "") {
            Toast.makeText(context, context.resources.getString(R.string.descripNotEntered), Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.location == "") {
            Toast.makeText(context, context.resources.getString(R.string.locationNotEntered), Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.year == "" || uiState.value.event.month == ""
            || uiState.value.event.day == ""
        ) {
            Toast.makeText(context, context.resources.getString(R.string.dateNotEntered), Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.minute == "" || uiState.value.event.hour == "") {
            Toast.makeText(context, context.resources.getString(R.string.timeNotEntered), Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.allergens == "") {
            Toast.makeText(context, context.resources.getString(R.string.allergensNotEntered), Toast.LENGTH_SHORT).show()
        } else if (uiState.value.event.surveyCode == "") {
            Toast.makeText(context, context.resources.getString(R.string.surveyCodeNotEntered), Toast.LENGTH_SHORT).show()
        } else{
            updateEvent()
            navController.navigate(Screen.EventScreenEmployee.route)
        }
    }
    /**
     * Updates the event in the database.
     * @author Sabirin Omar
     */

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
        Database.UpdateEvent(event, uiState.value.event.eventId)
    }
}
