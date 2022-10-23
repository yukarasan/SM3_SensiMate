package com.example.sensimate.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Column {
        QuickEntry()

        EventCard(
            title = "Coca Cola",
            distance = "2 km",
            address = "The Circular Lab"
        )

        EventCard(
            title = "Coca Cola light med ost og kage",
            distance = "349991 km",
            address = "The Cicular lab at Roskilde universitet"
        )
    }
}