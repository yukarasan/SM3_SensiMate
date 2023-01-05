package com.example.sensimate.data

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    fun insertEvent(event: Event){
        _uiState.value.events.add(event)
    }

    fun getEventById(id: String): Event {

        for (event in _uiState.value.events) {
            if (event.eventId == id) {
                return event
            }
        }
        return Event("NO SUCH EVENT")
    }

    fun setChosenEventId(id: String){
        _uiState.value.chosenSurveyId = id
    }

}
