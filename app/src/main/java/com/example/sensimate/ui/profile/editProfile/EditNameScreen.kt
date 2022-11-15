package com.example.sensimate.ui.profile.editProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun EditNameScreen() {
    Column() {
        Row() {
            Text(text = "Name")
            Text(text = "Checkbox")
        }
        Text(text = "Indsæt navn")
        Text(text = "Edit desc.")
    }
}