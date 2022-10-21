package com.example.sensimate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily

@Composable
fun EventCard() {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column() {
            Row {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        EventTitle(modifier = Modifier.padding(30.dp))
                    }
                    Row() {
                        Text(text = "Location ")
                        Column() {
                            Text(text = "2 km ")
                            Text(text = "The Circular Lab")
                        }
                    }
                }
                EventImage(modifier = Modifier.fillMaxWidth())
            }
            Text(text = "Processbar")
        }
    }
}

@Composable
fun EventTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Coca Cola",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        // color = Color.White
    )
}

@Composable
fun EventImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.beverages) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
}