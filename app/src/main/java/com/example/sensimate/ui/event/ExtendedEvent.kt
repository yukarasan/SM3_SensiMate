package com.example.sensimate.ui.event
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.home.EventInputField


class EventScreen {

    @Preview(showBackground = true)
    @Composable
    fun ExtendedEvent() {
        Card(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(red = 44, green = 44, blue = 59)
        ) {
            //button
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
                            Column {
                                EventTitle(title = "Coca Cola")
                                EventDiscription(discription = "Come and taste bla bla bla bla bla ")
                            }
                            Image(
                                painter = painterResource(
                                    id = R.drawable.beverages
                                ),
                                contentDescription = "",
                                modifier = Modifier.size(145.dp),
                            )
                        }
                        EventInputField()

                        Row() {
                            Allergens(title = "Allergens")
                        }
                        EventDiscription(discription = "N/A")

                        Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                            Column() {
                                EventTitle(title = "The Circular lab")
                            }
                            Discription(discription = "2 km")
                            Image(
                                painter = painterResource(
                                    id = R.drawable.location
                                ),
                                contentDescription = "",
                                modifier = Modifier.size(145.dp),
                            )
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
    private fun EventDiscription(discription: String, modifier: Modifier = Modifier){
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
    private fun Discription(discription: String, modifier: Modifier = Modifier){
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
}


