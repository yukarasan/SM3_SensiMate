package com.example.sensimate.ui.Event.extendedEvent

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
    val state = eventViewModel.uiState.collectAsState()

    var showFieldAlert by remember { mutableStateOf(false) }
    var showSecondFieldAlert by remember { mutableStateOf(false) }

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
                        shape = RoundedCornerShape(20.dp),
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

                            Column(
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                            ) {
                                InputField(
                                    onValueChange = {
                                        if (it.length <= 4) {
                                            eventViewModel.updateSurveyCodeString(
                                                surveyCode = it
                                            )
                                        }
                                    },
                                    text = state.value.event.chosenSurveyCode.value
                                )
                            }

                            Spacer(modifier = Modifier.size(10.dp))

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
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Button(
                            onClick = {
                                if (state.value.event.chosenSurveyCode.value.length < 4) {
                                    showFieldAlert = true
                                } else if (state.value.event.chosenSurveyCode.value ==
                                    state.value.event.surveyCode) {
                                    navController.popBackStack()
                                    navController.navigate(Screen.SurveyCreator.route)
                                } else {
                                    showSecondFieldAlert = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF8CB34D)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Enter Survey",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = manropeFamily
                            )
                        }
                    }
                }

            }

            if (showFieldAlert) {
                AlertDialog(onDismissRequest = { showFieldAlert = false }, text = {
                    Text(
                        "The provided survey code must be exactly 4 characters long. Please " +
                                "try again"
                    )
                }, confirmButton = {
                    Button(onClick = {
                        showFieldAlert = false
                    }) {
                        Text(text = "OK")
                    }
                })
            }

            if (showSecondFieldAlert) {
                AlertDialog(onDismissRequest = { showSecondFieldAlert = false }, text = {
                    Text(
                        "The survey code that you provided is not correct. Please try again."
                    )
                }, confirmButton = {
                    Button(onClick = {
                        showSecondFieldAlert = false
                    }) {
                        Text(text = "OK")
                    }
                })
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InputField(onValueChange: (String) -> Unit, text: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = text,
            onValueChange = onValueChange,
            label = { Label() },
            placeholder = { Placeholder() },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    Color(74, 75, 90),
                    shape = RoundedCornerShape(10.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
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