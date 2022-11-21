package com.example.sensimate.common.editProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sensimate.R

@Composable
fun CheckBox(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(199, 242, 219)),
        modifier = Modifier
            .height(90.dp)
            .width(90.dp)
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.checkmark),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}