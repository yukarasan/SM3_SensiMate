package com.example.sensimate.ui.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.home.EventInputField
import com.example.sensimate.ui.theme.BottonGradient
import com.example.sensimate.ui.theme.DarkPurple


@Preview(showBackground = true)
@Composable
fun ExtendedEvent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple, BottonGradient
                    )
                )
            )
    )
    OrangeBackButton()
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
                        top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp
                    )
                ) {

                    Row {
                        Column {
                            EventTitle(title = "Coca Cola")
                            EventDiscription(discription = "Come and taste the freshing sensation " +
                                    "of Coca Cola. Get a whole six pack for free")
                        }
                        Image(
                            painter = painterResource(
                                id = R.drawable.beverages
                            ),
                            contentDescription = "",
                            modifier = Modifier.size(145.dp),
                        )
                    }
                }
            }
            EventInputField()
            Allergens(title = "Allergens")
            EventDiscription(discription = "N/A")
            Row() {
                Column() {
                    EventTitle(title = "The Circular lab")
                }
                EventDiscription(discription = "2km")
            }
            Image(
                painter = painterResource(
                    id = R.drawable.location
                ),
                contentDescription = "",
                modifier = Modifier.size(320.dp),
            )
            EventDiscription(discription = "Helsingørmotervejen 15, lyngby")
            Bar(progress = 5.8f)
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
private fun EventDiscription(discription: String, modifier: Modifier = Modifier) {
    Text(
        text = discription,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)

    )
}

@Composable
private fun Allergens(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)
    )
}

@Composable
private fun Discription(discription: String, modifier: Modifier = Modifier) {
    Text(
        text = discription,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 50.dp)
            .width(220.dp)
    )
}

@Composable
private fun Bar(progress: Float) {
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

