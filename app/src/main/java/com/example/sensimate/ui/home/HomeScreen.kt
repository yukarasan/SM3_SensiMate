package com.example.sensimate.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        /*
        items(data) { item ->   // TODO: Use the commented version in the future:
            Item(item)
        }
         */

        item { QuickEntry() }
        item {
            EventCard(
                title = "Coca Cola",
                distance = "2 km",
                address = "The Circular Lab",
                progress = 0.5f
            )
        }
        item {
            EventCard(
                title = "Coca Cola light med ost og kage",
                distance = "349991 km",
                address = "The Cicular lab at Roskilde universitet",
                progress = 0.1f
            )
        }
        item {
            EventCard(
                title = "Coca Cola 1",
                distance = "2 km",
                address = "The Circular Lab",
                progress = 1.0f
            )
        }
        item {
            EventCard(
                title = "Coca Cola 2",
                distance = "2 km",
                address = "The Circular Lab",
                progress = 0.0f
            )
        }
        item {
            EventCard(
                title = "Coca Cola 3",
                distance = "2 km",
                address = "The Circular Lab",
                progress = 0.7f
            )
        }
        item {
            EventCard(
                title = "Coca Cola 4",
                distance = "2 km",
                address = "The Circular Lab",
                progress = 0.3f
            )
        }
    }
}