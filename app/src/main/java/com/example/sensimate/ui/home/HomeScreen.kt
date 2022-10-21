package com.example.sensimate.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Column() {
        EventCard(
            title = "Coca Cola",
            distance = "2 km",
            address = "The Circular Lab"
        )
        EventCard(
            title = "Sour Cream med ost",
            distance = "349991 km",
            address = "The cicular lab at Roskilde"
        )
    }
}