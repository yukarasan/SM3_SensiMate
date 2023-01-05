package com.example.sensimate.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.theme.DarkPurple

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventCard(
    title: String,
    timeOfEvent: String,
    address: String,
    onClick: () -> Unit,
    //function: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = DarkPurple,
        border = BorderStroke(2.dp, Color(154, 107, 254)),
        onClick = onClick
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
                        EventTitle(title)
                    }
                    Row {
                        LocationIcon()
                        Column(modifier = Modifier.padding(start = 5.dp)) {
                            TimeOfEvent(timeOfEvent)
                            Address(address)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EventTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)
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
private fun TimeOfEvent(time: String, modifier: Modifier = Modifier) {
    Text(
        text = time,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        color = Color.White,
        modifier = modifier.width(183.dp)
    )
}

@Composable
private fun Address(address: String, modifier: Modifier = Modifier) {
    Text(
        text = address,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        color = Color.White,
        modifier = modifier.width(183.dp)
    )
}

/**
 * The following out-commented code is functions that we could implement in the future, that would
 * be nice to have on the app. For now, they are not necessary, which is why we have left them out.
 */

/*
@Composable
private fun EventImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.beverages)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(120.dp)
            .padding(top = 10.dp, end = 20.dp)
    )
}

@Composable
private fun ProgressBar(progress: Float) {
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
            progress = progress
        )
    }
}
 */