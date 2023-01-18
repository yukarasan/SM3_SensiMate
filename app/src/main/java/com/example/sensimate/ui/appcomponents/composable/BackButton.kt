package com.example.sensimate.ui.appcomponents.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sensimate.R

/**
 * This composable is an orange back button which is used several places within the app,
 * and therefore we decided it would be beneficial to have it defined here as a public method,
 * other screens can use it.
 * @author Yusuf Kara
 */
@Composable
fun OrangeBackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(239, 112, 103)),
        modifier = Modifier.size(55.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}