package com.example.sensimate.ui.Event.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import com.example.sensimate.data.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * The EventDataViewModel is a view model class used to store and manage the data for the event
 * screen in the application.
 * It uses the fetchListOfEvents() function to populate the events list and updates the state
 * of the events.
 * It uses the mutableStateOf() function to keep the track of the latest state of the events.
 * It has a getListOfEvents() function that is responsible for populating the state.
 */
class EventDataViewModel : ViewModel() {
    val state = mutableStateOf(EventDataState())
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getListOfEvents()
    }

    fun getListOfEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            val eventList = Database.fetchListOfEvents()
            state.value = state.value.copy(events = eventList)
            _isLoading.value = false
        }
    }

    fun getEventBySurveyCode(surveyCode: String, onComplete: (Event?) -> Unit) {
        val eventsRef = Firebase.firestore.collection("events")
        eventsRef
            .whereEqualTo("surveyCode", surveyCode)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val event = querySnapshot.documents[0].toObject(Event::class.java)
                    onComplete(event)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("EventDataViewModel", "Error getting event by survey code", exception)
                onComplete(null)
            }
    }
}