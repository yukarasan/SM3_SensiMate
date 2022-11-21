package com.example.sensimate.ui.Event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.BottonGradient
import com.example.sensimate.ui.theme.DarkPurple

//@Preview(showBackground = true)
@Composable
fun RegisterScreen(navController: NavController){
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
    Column() {
        Spacer(modifier = Modifier.size(240.dp))
        Title(title = "Congratulations! You have now registered this event")
        Spacer(modifier = Modifier.size(30.dp))
        Discription(discription = "We are looking forward to see you")
    }
    //OrangeBackButton ({navController.popBackStack()})
    Column(modifier = Modifier.padding(15.dp, 10.dp)) {
        OrangeBackButton({navController.popBackStack()})
    }
}
@Composable
private fun Title(title: String, modifier: Modifier = Modifier){
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 39.sp,
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
            .width(220.dp)
    )
}
@Composable
private fun Discription(discription : String, modifier: Modifier = Modifier){
    Text(
        text = discription,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)
    )
}