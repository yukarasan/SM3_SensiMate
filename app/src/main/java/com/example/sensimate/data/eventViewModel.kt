package com.example.sensimate.data

import androidx.lifecycle.ViewModel
import com.example.sensimate.data.questionandsurvey.QuestionsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(Event())
    val uiState: StateFlow<Event> = _uiState.asStateFlow()

}
