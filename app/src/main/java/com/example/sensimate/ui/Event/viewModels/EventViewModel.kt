package com.example.sensimate.ui.Event.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel: ViewModel() {
    // UI state
    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState: StateFlow<EventUiState> = _eventUiState.asStateFlow()

}

