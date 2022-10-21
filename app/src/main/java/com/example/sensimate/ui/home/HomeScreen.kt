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
            title = "Coca Cola ligth med ost og kage og noget andet til",
            distance = "349991 km",
            address = "Cicular lab at Roskilde"
        )
    }
}