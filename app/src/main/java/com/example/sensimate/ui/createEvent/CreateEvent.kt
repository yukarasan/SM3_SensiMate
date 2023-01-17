package com.example.sensimate.ui.createEvent

import AnswerViewModel
import QuestionPageViewModel
import TextAnswerViewModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.db
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.*
import kotlinx.coroutines.flow.asStateFlow
import java.util.*


@Preview(showBackground = true)
@Composable
fun CreateEventPreview() {
   //CreateEventScreen(rememberNavController())
    //QuestionPageScreen(rememberNavController())
    //CreateMultpleChoiceQuestionScreen(rememberNavController())
    //CreateTextAnswerQuestionScreen(rememberNavController())
}

var docId: String = ""
@Composable
fun CreateEventScreen(navController: NavController, createEventViewModel: CreateEventViewModel) {

    val state = createEventViewModel._uistate.collectAsState()
    val maxChar = 4
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        AddPhoto(
            modifier = Modifier
                .padding(end = 25.dp, top = 20.dp)
                .size(20.dp), id = R.drawable.ic_add_circle_outlined
        )
        TextToPhoto(
            modifier = Modifier.padding(end = 10.dp))
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Spacer(modifier = Modifier.size(55.dp))
            TextFiledTitleText(state.value.titleText) { state.value.titleText.value = it }
            Spacer(modifier = Modifier.size(27.dp))
            TextFiledDescriptionText(state.value.descriptionText) { state.value.descriptionText.value = it }
            Spacer(modifier = Modifier.size(55.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),

                shape = RoundedCornerShape(15.dp),
                backgroundColor = Color(0xFF4D3B72)


            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.sentimatelogo
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(800.dp)
                        .blur(1.dp)
                        .alpha(0.2f),
                    contentScale = ContentScale.Crop,

                    )
                TextFiledLocationText(state.value.locationText) { state.value.locationText.value = it }
                TextFiledAllergensText(state.value.allergensText) { state.value.allergensText.value = it }
                TextFiledSurveyCodeText(state.value.surveyCodeText) {
                    if (it.length <= maxChar) state.value.surveyCodeText.value = it
                }

                ChooseEventDate(
                    LocalContext.current,
                    myYear = state.value.myYear,
                    myMonth = state.value.myMonth,
                    myDay = state.value.myDay
                )

                ChooseTime(context = LocalContext.current, myHour = state.value.myHour, myMinute = state.value.myMinute)

                Column(

                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.size(75.dp))
                    Spacer(modifier = Modifier.size(250.dp))

                    Button(
                        onClick = {
                            createEventViewModel.check(context,navController)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                        modifier = Modifier.size(240.dp, 50.dp),
                        enabled = true

                    ) {

                        Text(
                            text = "Create Event",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = manropeFamily
                        )
                    }
                    Spacer(modifier = Modifier.size(55.dp))
                    Button(
                        onClick = {
                            navController.navigate(Screen.EventScreenEmployee.route){
                                navController.popBackStack()
                            } },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedColor),
                        modifier = Modifier.size(240.dp, 50.dp)
                    ) {
                        Text(
                            text = "Go Back",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = manropeFamily
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ChooseEventDate(
    context: Context,
    myYear: MutableState<String>,
    myMonth: MutableState<String>,
    myDay: MutableState<String>,
) {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR,0)

    // Create state variables to store the selected year, month, and day
    val selectedYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val selectedMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val selectedDay = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    val hasChosen = remember {
        mutableStateOf(false)
    }

    Log.d("myyear.value", myYear.value)
    Log.d("mymonth.value", myMonth.value)
    Log.d("myday.value", myDay.value)


    var text by remember { mutableStateOf(("")) }

    val datePickerLog =
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayofMonth: Int ->
                text = "$dayofMonth/${month + 1}/$year"
                // Update the selected year, month, and day
                selectedYear.value = year
                selectedMonth.value = month
                selectedDay.value = dayofMonth
                hasChosen.value = true
            }, selectedYear.value, selectedMonth.value, selectedDay.value
        )

    if (hasChosen.value) {
        myYear.value = selectedYear.value.toString()
        myMonth.value = (selectedMonth.value + 1).toString()
        myDay.value = selectedDay.value.toString()
    }

    datePickerLog.datePicker.minDate = calendar.timeInMillis
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Green,
            backgroundColor = Color.Transparent
        ),
        enabled = false,
        value = text,
        label = { Text(text = "Date For The Event", color = Color(0xFFB874A6)) },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 65.dp, 1.dp, 1.dp)
            .clickable { datePickerLog.show() },
    )
}

@Composable
fun ChooseTime(context: Context,
               myHour: MutableState<String>,
               myMinute: MutableState<String>){
    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val iHour = remember { mutableStateOf(mCalendar.get(Calendar.HOUR_OF_DAY)) }
    val iMinute = remember { mutableStateOf(mCalendar.get(Calendar.MINUTE)) }



    // Value for storing time as a string
    var mTime by remember { mutableStateOf(("")) }
    val hasChosen = remember {
        mutableStateOf(false)
    }
    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        context,
        {_: TimePicker, mHour : Int, mMinute: Int ->
            mTime = "$mHour:$mMinute"
        iHour.value = mHour
        iMinute.value = mMinute
        hasChosen.value = true
        }, iHour.value, iMinute.value, false
    )

    if (hasChosen.value) {
        myHour.value = iHour.value.toString()
        myMinute.value = iMinute.value.toString()
    }

    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Green,
            backgroundColor = Color.Transparent
        ),
        enabled = false,
        value = mTime,
        label = {
            Text(text = "Time Of The Event", color = Color(0xFFB874A6)) },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 128.dp, 1.dp, 1.dp)
            .clickable { mTimePickerDialog.show() },
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledTitleText(titleText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = titleText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Title",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledDescriptionText(descriptionText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = descriptionText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Description",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})
            )

    }
}

@Composable
fun ContentColorComponent(
    contentColor: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        content = content
    )
}


@Composable
fun AddPhoto(modifier: Modifier = Modifier, id: Int) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "HEJ MED DIG ",
            modifier = modifier
        )
}

@Composable
fun TextToPhoto(modifier: Modifier) {
    Text(
        text = "Add Photo",
        color = Color(0xFFB874A6), fontSize = 11.sp,
        maxLines = 1,
        modifier = modifier


    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledLocationText(locationText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = locationText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Location",
                    color = Color(0xFFB874A6)
                )
            }, trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.redlocationicon),
                        modifier = Modifier
                            .size(20.dp),
                        contentDescription = ""
                    )

                }
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,

            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
            modifier = Modifier
                .padding(1.dp, 2.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledAllergensText(allergensText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = allergensText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Allergens",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,

            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
            modifier = Modifier
                .padding(1.dp, 191.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledSurveyCodeText(surveyCodeText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = surveyCodeText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Survey Code (4 Digit Code)",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),

            modifier = Modifier
                .padding(1.dp, 254.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

var nonQuestion: Int = 0
// figur 2
@Composable
fun QuestionPageScreen(navController: NavController,questionPageViewModel: QuestionPageViewModel) {
    val state = questionPageViewModel._uistate.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            //.size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )
    Column(
        Modifier
            .padding(top = 150.dp)
            .fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(15.dp),
            shape = RoundedCornerShape(14.dp),
            backgroundColor = Color(red = 44, green = 44, blue = 59)

        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                DropDownMenu(selectedQuestion = state.value.selectedQuestion)
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Create a Question",
                    color = Color(0xFFB874A6),
                    fontSize = 26.sp,
                    modifier = Modifier
                        .padding(start = 35.dp, top = 220.dp)

                )
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                AddPhoto(
                    modifier = Modifier
                        .padding(end = 55.dp, top = 225.dp)
                        .size(25.dp)
                        .clickable(enabled = true,
                            onClick = {questionPageViewModel.checkQuestion(navController,context)}),
                    id = R.drawable.redaddplus
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = { questionPageViewModel.checkNonQuestion(navController,context)},
                    shape = CircleShape,
                    border = BorderStroke(1.dp, color = Color.Green),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            red = 44,
                            green = 44,
                            blue = 59
                        )
                    ),
                    modifier = Modifier
                        .padding(top = 430.dp)
                        .size(240.dp, 50.dp)
                ) {
                    Text(
                        text = "Done",
                        color = Color.Green,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = manropeFamily
                    )
                }
                Spacer(modifier = Modifier.size(60.dp))
            }
        }
    }
}

//TODO Figur 3
@SuppressLint("SuspiciousIndentation")
@Composable
fun CreateMultpleChoiceQuestionScreen(navController: NavController, answerViewModel: AnswerViewModel) {
    val state = answerViewModel._uistate.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.size(27.dp))
            Text(
                text = "Multiple-choice",
                color = Color(0xEFFF7067),
                fontSize = 20.sp

            )
            Spacer(modifier = Modifier.size(55.dp))

            TextFiledQuestionText(state.value.questionText) { state.value.questionText.value = it }
            Spacer(modifier = Modifier.size(27.dp))
            TextFiledAnswerText("Answer 1", state.value.answerText1) { state.value.answerText1.value = it }
            Spacer(modifier = Modifier.size(27.dp))
            TextFiledAnswerText("Answer 2", state.value.answerText2) { state.value.answerText2.value = it }
            if (state.value.answerText2.value != "") {
                Spacer(modifier = Modifier.size(27.dp))
                TextFiledAnswerText("Answer 3", state.value.answerText3) { state.value.answerText3.value = it }
            }
            if (state.value.answerText3.value != "") {
                Spacer(modifier = Modifier.size(27.dp))
                TextFiledAnswerText("Answer 4", state.value.answerText4) { state.value.answerText4.value = it }
            }
            if (state.value.answerText4.value != "") {
                Spacer(modifier = Modifier.size(27.dp))
                TextFiledAnswerText("Answer 5", state.value.answerText5) { state.value.answerText5.value = it }
            }
            Spacer(modifier = Modifier.size(55.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = state.value.checkedState.value,
                    onCheckedChange = {
                        state.value.checkedState.value = it
                    },
                    colors = CheckboxDefaults
                        .colors(
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White,
                            checkedColor = Purple500,

                            disabledColor = Color.White,
                            disabledIndeterminateColor = Color.White,
                        )
                )
                Text(
                    text = "One Answer Only",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontFamily = manropeFamily
                )
                Text(
                    text = " *",
                    color = Color.Red,
                    fontWeight = FontWeight.Light,
                    fontFamily = manropeFamily
                )
            }

            Button(
                onClick = {answerViewModel.multipleAnswer(navController,context)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                modifier = Modifier.size(240.dp, 50.dp)
            ) {
                Text(
                    text = "Create Question",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = manropeFamily
                )
            }

            Spacer(modifier = Modifier.size(55.dp))
            Button(
                onClick = { answerViewModel.goBack(navController)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = RedColor),
                modifier = Modifier.size(240.dp, 50.dp)
            ) {
                Text(
                    text = "Go Back",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = manropeFamily
                )
            }

        }
    }
}


// TODO FIGUR 4
@Composable
fun CreateTextAnswerQuestionScreen(navController: NavController, textAnswerViewModel: TextAnswerViewModel) {
    val state = textAnswerViewModel._uistate.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.size(27.dp))
            Text(
                text = "Text Answer Question",
                color = Color(0xEFFF7067),
                fontSize = 20.sp

            )
            Spacer(modifier = Modifier.size(55.dp))

            TextFiledQuestionText(state.value.questionText) { state.value.questionText.value = it }
            Spacer(modifier = Modifier.size(55.dp))

            Button(

                onClick = { textAnswerViewModel.textAnswer(navController,context)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                modifier = Modifier.size(240.dp, 50.dp)
            ) {
                Text(
                    text = "Create Question",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = manropeFamily
                )
            }

            Spacer(modifier = Modifier.size(55.dp))
            Button(
                onClick = { textAnswerViewModel.goBack(navController) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = RedColor),
                modifier = Modifier.size(240.dp, 50.dp)
            ) {
                Text(
                    text = "Go Back",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = manropeFamily
                )
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledQuestionText(questionText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = questionText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Question",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})


            )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledAnswerText(text: String, answerText: MutableState<String>, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = answerText.value,
            onValueChange = textChange,
            label = {
                Text(
                    text = text,
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})


            )

    }
}

@Composable
fun DropDownMenu(selectedQuestion: MutableState<String>) {

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Multiple-Choice Question", "Text Answer Question")


    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
        OutlinedTextField(
            value = selectedQuestion.value,
            onValueChange = { selectedQuestion.value = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color(0xFFB874A6),
                disabledLabelColor = Color(0xFFB874A6),
                focusedBorderColor = Color(0xFFB874A6),
                disabledPlaceholderColor = Color.White,
                disabledTextColor = Color(0xEFFF7067),
                disabledBorderColor = Color(0xFFB874A6)
            ),
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .width(150.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Question Type") },
            trailingIcon = {
                Icon(
                    icon, "",
                    Modifier.clickable { expanded = !expanded }, tint = Color(0xFFB874A6)
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                .background(Color(red = 44, green = 44, blue = 59))
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedQuestion.value = label
                    expanded = false
                }) {
                    Text(text = label, color = Color(0xFFB874A6))
                }
            }
        }
    }
}
