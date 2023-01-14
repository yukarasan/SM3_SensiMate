package com.example.sensimate.ui.Event.viewModels

import com.example.sensimate.data.Event

/**
 * The EventScreenState data class represents the state of the event screen in the application.
 * It contains a MutableList of Event objects representing the events to be displayed on the screen.
 * @param events MutableList of Event objects that needs to be displayed on the screen.
 * @author Yusuf Kara
 */
data class EventDataState(
    val events: MutableList<Event>? = null
)
