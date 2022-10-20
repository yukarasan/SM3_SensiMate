package com.example.sensiMate.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Event() {
    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column() {
            Text(text = "Test")
            Text(text = "Test")
        }
    }
}