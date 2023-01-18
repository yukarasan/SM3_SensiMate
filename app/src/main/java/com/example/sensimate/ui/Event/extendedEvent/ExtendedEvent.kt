package com.example.sensimate.ui.Event.extendedEvent

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.EventViewModel
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple

/**
 * ExtendedEvent is a composable function uses the eventViewModel to fetch the chosen event by
 * its id and display it's details.
 * It displays a card displaying the event's details, such as title, description, survey code,
 * allergens, time, and location.
 * This composable was originally made by Sabirin, but has later been modified by Yusuf.
 * @author Sabirin & Yusuf
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ExtendedEvent(
    navController: NavController,
    eventViewModel: EventViewModel = viewModel()
) {
    val eventState = eventViewModel.uiState
    val chosenEvent = eventViewModel.getEventById(eventState.value.chosenSurveyId)
    val state = eventViewModel.uiState.collectAsState()

    /*
    The following states could in theory be a part of the viewModel, but since they are only
    used within this composable, it is easier and more accessible to have them defined here.
     */
    var surveyCodeNotLongEnough by remember { mutableStateOf(false) }
    var surveyCodeNotCorrect by remember { mutableStateOf(false) }

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
                OrangeBackButton(
                    onClick = {
                        navController.popBackStack()
                    }
                )
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
                                Allergens(
                                    title = stringResource(id = R.string.allergens),
                                    allergen = chosenEvent.allergens
                                )
                            }

                            Spacer(modifier = Modifier.size(15.dp))

                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Title(title = stringResource(id = R.string.location))

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
                                    surveyCodeNotLongEnough = true
                                } else if (state.value.event.chosenSurveyCode.value ==
                                    state.value.event.surveyCode
                                ) {
                                    navController.popBackStack()
                                    navController.navigate(Screen.SurveyCreator.route)
                                } else {
                                    surveyCodeNotCorrect = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF8CB34D)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.enterSurvey),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = manropeFamily
                            )
                        }
                    }
                }
            }

            if (surveyCodeNotLongEnough) {
                AlertDialog(
                    onDismissRequest = { surveyCodeNotLongEnough = false },
                    text = {
                        Text(stringResource(id = R.string.surveyCodeNotLongEnough))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                surveyCodeNotLongEnough = false
                            }
                        ) {
                            Text(text = stringResource(id = R.string.ok))
                        }
                    }
                )
            }

            if (surveyCodeNotCorrect) {
                AlertDialog(
                    onDismissRequest = { surveyCodeNotCorrect = false },
                    text = {
                        Text(stringResource(id = R.string.surveyCodeNotCorrect))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                surveyCodeNotCorrect = false
                            }
                        ) {
                            Text(text = stringResource(id = R.string.ok))
                        }
                    }
                )
            }
        }
    }
}

/**
 * This composable displays the title of an event on the extended event screen.
 * @author Yusuf Kara
 */
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

/**
 * This composable displays the description of an event on the extended event screen.
 * @author Yusuf Kara
 */
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

/**
 * This composable displays the time of an event on the extended event screen.
 * @author Yusuf Kara
 */
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

/**
 * This composable displays the address of an event on the extended event screen.
 * @author Yusuf Kara
 */
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

/**
 * This composable displays the allergens of an event on the extended event screen.
 * @author Yusuf Kara
 */
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

/**
 * This composable function creates an input field for the user to enter a survey code.
 * @param onValueChange the value of the text field changes
 * @param text the initial text displayed in the input field
 * This composable was originally made by Yusuf, but later modified by Sabirin
 * @author Sabirin and Sabirin
 */
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
            maxLines = 1
        )
    }
}

/**
 * Label is a composable function that renders a text element with the text
 * "Enter event code" in the text field.
 * @author Yusuf Kara
 */
@Composable
private fun Label() {
    Text(
        text = stringResource(id = R.string.enterEventCode),
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}

/**
 * Placeholder is a composable function that renders a text element with the text
 * "Enter event code to open survey" in the text field.
 * @author Yusuf Kara
 */
@Composable
private fun Placeholder() {
    Text(
        text = stringResource(id = R.string.enterEventCodeToOpenSurvey),
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = Color.White
    )
}

@Preview
@Composable
fun ExtendedEventScreenPreview() {
    ExtendedEvent(navController = rememberNavController())
}