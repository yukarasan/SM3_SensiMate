package com.example.sensimate.ui.Event.extendedEvent

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.data.EventViewModel
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ExtendedEvent(
    navController: NavController,
    eventViewModel: EventViewModel = viewModel()
) {
    val eventState = eventViewModel.uiState
    val chosenEvent = eventViewModel.getEventById(eventState.value.chosenSurveyId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                OrangeBackButton(onClick = { navController.popBackStack() })
            }

            LazyColumn() {
                item {
                    Card(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(35.dp),
                        border = BorderStroke(2.dp, Color(154, 107, 254)),
                        backgroundColor = DarkPurple
                    ) {
                        Column {
                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Title(title = chosenEvent.title)
                                Description(description = chosenEvent.description)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                ) {
                                    InputField() { }
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                ) {
                                    Button(
                                        onClick = { navController.navigate(Screen.SurveyCreator.route) },
                                        colors = ButtonDefaults.buttonColors(Color(0xFF8CB34D)),
                                        modifier = Modifier
                                            .size(width = 50.dp, height = 50.dp)
                                            .padding(bottom = 10.dp)
                                    ) {
                                        Text(
                                            text = "Go",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            color = Color.White,
                                            fontFamily = manropeFamily
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(15.dp))

                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Allergens(title = "Allergens", allergen = chosenEvent.allergens)
                            }

                            Spacer(modifier = Modifier.size(15.dp))

                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Title(title = "Location")

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Time(hour = chosenEvent.hour, minute = chosenEvent.minute)
                                    Address(address = chosenEvent.location)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp, bottom = 8.dp)
            .width(220.dp)
    )
}

@Composable
private fun Description(description: String, modifier: Modifier = Modifier) {
    Text(
        text = description,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun Time(hour: String, minute: String, modifier: Modifier = Modifier) {
    Text(
        text = "$hour:$minute",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White,
        modifier = modifier.padding(start = 8.dp, end = 16.dp)
    )
}

@Composable
private fun Address(address: String, modifier: Modifier = Modifier) {
    Text(
        text = address,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White,
        modifier = modifier.padding(end = 8.dp)
    )
}

@Composable
private fun Allergens(title: String, allergen: String, modifier: Modifier = Modifier) {
    Column() {
        Text(
            text = title,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = allergen,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun InputField(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
    ) {
        // ---------------------------------------------------------------------------
        //TODO: Needs state hoisting
        var text by remember { mutableStateOf(TextFieldValue("")) }
        // ---------------------------------------------------------------------------
        TextField(
            value = text,
            onValueChange = { it -> text = it },
            label = { Label() },
            placeholder = { Placeholder() },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .width(240.dp)
                .height(40.dp)
                .padding(end = 10.dp)
                .background(
                    Color(74, 75, 90),
                    shape = RoundedCornerShape(35.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1 //TODO: maxLines not working. Fix this.
        )
    }
}

@Composable
private fun Label() {
    Text(
        text = "Enter event code", //TODO: Make text as recourse
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}

@Composable
private fun Placeholder() {
    Text(
        text = "Enter event code here to open the survey", //TODO: Make text as recourse
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}

@Preview
@Composable
fun ExtendedEventScreenPreview() {
    ExtendedEvent(navController = rememberNavController())
}