package com.example.sensimate.ui.survey

import androidx.compose.foundation.*
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.navigation.NavController
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.*


@Composable
fun Survey2(title: String, navController: NavController) {
    var selectedOption by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0.0f to DarkPurple,
                    0.5f to BottonGradient
                )
            )
            .fillMaxSize()
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        items(1) {
            OrangeBackButton({navController.navigate(Screen.EventScreen.route)})
        }
        items(1) {
            ProgressPreview()
        }
        items(1) {
            Question(title)
        }
        items(1) {
            SurveyTitle(title)
        }
        items(1) {
            Information2()
        }
        items(1) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
            ) {
                PreviousButton(onClick = { navController.navigate(Screen.Survey.route) } )
                NextButton(onClick = { navController.navigate(Screen.Survey3.route) } )
            }
        }
    }
}

/*
@Composable
fun Survey2(title: String, navController: NavController) {
    var selectedOption by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0.0f to DarkPurple,
                    0.5f to BottonGradient
                )
            )
            .fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        OrangeBackButton({navController.navigate(Screen.EventScreen.route)})
        ProgressPreview()
        Question(title)
        SurveyTitle(title)
        Information2()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
        ) {
            PreviousButton(onClick = { navController.navigate(Screen.Survey.route) } )
            NextButton(onClick = { navController.navigate(Screen.Survey3.route) } )
        }
    }
}

 */


@Composable
private fun ProgressPreview() {
    LinearProgressIndicator(
        modifier = Modifier
            .padding(top = 20.dp, start = 0.dp)
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(15.dp)),
        backgroundColor = darkpurple,
        color = lightpurple, //progress color
        progress = 0.50f //TODO:  Needs state hoisting in future.

    )
}

@Composable
fun Information2() {
    // Add a state variable to track the selected option
    var selectedOption by remember { mutableStateOf(0) }
    var listener: ((option: Int, value: Boolean) -> Unit)? = { i: Int, b: Boolean ->
        selectedOption = i
    }

    // Define a list of options and their corresponding titles
    val options = listOf("Very Likely", "Likely", "Neutral", "Unlikely", "Very Unlikely")
   // val options = remember { MutableList<MyQuestion> = emptyList<MyQuestion>().toMutableList() }

    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            // Iterate through the options and create the rows with the options
            for (i in options.indices) {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, top = if (i == 0) 10.dp else 25.dp, bottom = if (i == options.size - 1) 15.dp else 0.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Pass the selectedOption state variable as a parameter to RoundedCheckView
                    RoundedCheckView(listener, selectedOption, option = i)
                    Option(options[i])
                    Spacer(modifier = Modifier.width((120.dp)))
                }
            }
        }
    }
}

@Composable
fun RoundedCheckView(listener: ((Int, Boolean)-> Unit)? = null, state: Int, option: Int) {
    // Add a state variable to track whether the checkbox is checked
    val isChecked = (state == option)
    val circleSize = remember { mutableStateOf(22.dp) }
    val circleSize2 = remember { mutableStateOf(12.dp) }
    val circleThickness = remember { mutableStateOf(2.dp) }
    val color = remember { mutableStateOf(GreyColor) }

    if (isChecked) {
        circleSize.value = 22.dp
        circleThickness.value = 2.dp
        color.value = lightpurple
    }
    else {
    circleSize.value = 22.dp
    circleThickness.value = 2.dp
    color.value = GreyColor
    }

        Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            // Add a clickable modifier to the Row element
            .clickable(onClick = {
                // Update the isChecked state variable
              //  isChecked.value = !isChecked.value

                listener?.invoke(option, isChecked)
            })
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(22.dp)
                .background(if(isChecked) color.value else GreyColor)
                .padding(2.dp)
                .clip(CircleShape)
                .background(darkbluegrey) ,
            contentAlignment = Center
        ) {
             if (isChecked || state == option) {
                Box(
                    modifier = Modifier
                        .size(circleSize2.value)
                        .clip(CircleShape)
                        .background(color.value)
                )
            }
        }
    }
}

@Composable
private fun Option(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 5.dp, start = 20.dp)
    )
}















