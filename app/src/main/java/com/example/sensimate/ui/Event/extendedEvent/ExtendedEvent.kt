package com.example.sensimate.ui.Event.extendedEvent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.navigation.Screen
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.home.EventInputField
import com.example.sensimate.ui.theme.BottonGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.LightColor

//@Preview(showBackground = true)
@Composable
fun ExtendedEvent(navController: NavController) {
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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .padding(start = 30.dp, end = 5.dp, top = 70.dp)
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
                                Title(title = "Coca Cola")
                                Discription(
                                    discription = "Come and taste the freshing sensation " +
                                            "of Coca Cola. Get a whole six pack for free."
                                )
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
                Discription(discription = "N/A")
                Spacer(modifier = Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column() {
                        Title(title = "The Circular lab")
                    }
                    Discription(discription = "30km")
                }
                Image(
                    painter = painterResource(
                        id = R.drawable.location
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                )
                Discription(discription = "Helsingørmotervejen 15, 2500 lyngby")
                Bar(progress = 0.39f)
            }
        }
        Spacer(modifier = Modifier.size(25.dp))
        RegisterButton(onClick = {navController.navigate(Screen.Registerscreen.route)})
    }

    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
        OrangeBackButton(onClick = {navController.popBackStack()})
    }
}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
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
private fun Discription(discription: String, modifier: Modifier = Modifier) {
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
                .clip(RoundedCornerShape(30.dp)),
            backgroundColor = Color(red = 63, green = 69, blue = 81),
            color = Color(red = 199, green = 242, blue = 219), //progress color
            progress = progress //TODO:  Needs state hoisting in future.
        )
    }
}

@Composable
private fun RegisterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
        modifier = Modifier.size(345.dp, 60.dp),

    ) {
        Text(
            text = "Register",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.White,
            fontFamily = manropeFamily
        )
    }
}
