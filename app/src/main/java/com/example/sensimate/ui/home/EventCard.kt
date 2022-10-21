package com.example.sensimate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            Row {
                Column(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                ) {
                    Row {
                        EventTitle()
                    }
                    Row() {
                        LocationIcon()
                        Column(modifier = Modifier.padding(start = 20.dp)) {
                            DistanceToEvent()
                            Address()
                        }
                    }
                }
                EventImage(modifier = Modifier.fillMaxWidth())
            }
            ProgressBar()
        }
    }
}

@Composable
private fun EventTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Coca Cola",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        color = Color.White,
        modifier = modifier.padding(start = 8.dp)
    )
}

@Composable
private fun LocationIcon() {
    Icon(
        painter = painterResource(id = R.drawable.icon_location),
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(40.dp)
    )
}

@Composable
private fun DistanceToEvent(modifier: Modifier = Modifier) {
    Text(
        text = "2 km",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
private fun Address(modifier: Modifier = Modifier) {
    Text(
        text = "The Circular Lab",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
private fun EventImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.beverages) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier.size(120.dp).padding(top = 10.dp)
    )
}

@Composable
private fun ProgressBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(15.dp)),
            backgroundColor = Color(red = 63, green = 69, blue = 81),
            color = Color(red = 199, green = 242, blue = 219), //progress color
            progress = 0.20f //TODO:  Needs state hoisting in future.
        )
    }
}