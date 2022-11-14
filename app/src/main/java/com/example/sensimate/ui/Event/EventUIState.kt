package com.example.sensimate.ui.Event

/**
 * Data class that represents the event UI state
 */
data class EventUiState(
    val title: String = "",
    val distanceToEvent: Int = 0,
    val address: String = "",
    val isRegistered: Boolean = false
)
