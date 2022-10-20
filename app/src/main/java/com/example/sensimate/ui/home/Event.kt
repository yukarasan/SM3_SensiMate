package com.example.sensimate.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sensimate.model.manropeFamily

@Composable
fun EventCard() {
    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column {
            EventTitle()

            Row() {
                Text(text = "Location ")
                Column() {
                    Text(text = "2 km ")
                    Text(text = "The Circular Lab")
                }
            }
            Text(text = "Processbar")
        }
    }
}

@Composable
fun EventTitle() {
    Text(text = "Coca Cola", fontFamily = manropeFamily, fontWeight = FontWeight.ExtraBold)
}