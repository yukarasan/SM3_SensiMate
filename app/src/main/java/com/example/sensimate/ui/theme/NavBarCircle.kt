package com.example.sensimate.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavBarCircle() {
    Canvas(
        modifier = Modifier
            .size(13.dp)
            .border(
                color = Color(155, 107, 254),
                width = 2.dp, shape = RoundedCornerShape(100)
            )
    ) {
        drawCircle(color = Color(155, 107, 254))
    }
}