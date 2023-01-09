package com.example.sensimate.ui.Event.createEvent

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.*
import com.google.firebase.firestore.DocumentReference
import java.util.*


@Preview(showBackground = true)
@Composable
fun CreateEventPreview() {
   //CreateEventScreen(rememberNavController())
    QuestionPageScreen(rememberNavController())
    //CreateMultpleChoiceQuestionScreen(rememberNavController())
    //CreateTextAnswerQuestionScreen(rememberNavController())
}

var docId: String = ""
@Composable
fun CreateEventScreen(navController: NavController) {
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("") }
    var allergensText by remember { mutableStateOf("") }
    var surveyCodeText by remember { mutableStateOf("") }
    var timeText by remember { mutableStateOf("") }
    val myYear = remember { mutableStateOf("") }
    val myMonth = remember { mutableStateOf("") }
    val myDay = remember { mutableStateOf("") }
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
                        BottonGradient
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
            TextFiledTitleText(titleText) { titleText = it }
            Spacer(modifier = Modifier.size(27.dp))
            TextFiledDescriptionText(descriptionText) { descriptionText = it }
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
                TextFiledLocationText(locationText) { locationText = it }
                TextFiledAllergensText(allergensText) { allergensText = it }
                TextFiledSurveyCodeText(surveyCodeText) {
                    if (it.length <= maxChar) surveyCodeText = it
                }

                ChooseBirthDate(
                    LocalContext.current,
                    myYear = myYear,
                    myMonth = myMonth,
                    myDay = myDay
                )
                /*
                day = myDay.value
                month = myMonth.value
                year = myYear.value

                 */

                TextFileTimeText(timeText) {
                    if (it.length <= maxChar) timeText = it
                }
                Column(

                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.size(75.dp))
                    Spacer(modifier = Modifier.size(250.dp))

                    Button(
                        onClick = {
                            if (titleText == "") {
                                Toast.makeText(
                                    context,
                                    "Title was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (descriptionText == "") {
                                Toast.makeText(
                                    context,
                                    "Description was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (locationText == "") {
                                Toast.makeText(
                                    context,
                                    "Location was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (myYear.value == "") {
                                Toast.makeText(
                                    context,
                                    "Date was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (timeText == "") {
                                Toast.makeText(
                                    context,
                                    "Time was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (allergensText == "") {
                                Toast.makeText(
                                    context,
                                    "Allergens was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (surveyCodeText == "") {
                                Toast.makeText(
                                    context,
                                    "SurveyCode was not entered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Database.createEvent(
                                    title = titleText,
                                    description = descriptionText,
                                    allergens = allergensText,
                                    location = locationText,
                                    surveyCode = surveyCodeText,
                                    time = timeText,
                                    day = myDay.value,
                                    month = myMonth.value,
                                    year = myYear.value
                                )

                                navController.navigate(Screen.QuestionPageScreen.route)
                            }
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
                        onClick = { navController.navigate(Screen.EventScreenEmployee.route) },
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
fun ChooseBirthDate(
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
        value = if(myDay.value.isNotEmpty()) "${myDay.value}/${myMonth.value}/${myYear.value}" else "",
        label = { Text(text = "Date For The Event", color = Color(0xFFB874A6)) },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 65.dp, 1.dp, 1.dp)
            .clickable { datePickerLog.show() },
    )
}


@Composable
fun TextFiledTitleText(titleText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = titleText,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Title",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) }
        )
    }
}

@Composable
fun TextFiledDescriptionText(descriptionText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = descriptionText,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Description",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) }
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


@Composable
fun TextFiledLocationText(locationText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = locationText,
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
            modifier = Modifier
                .padding(1.dp, 2.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun TextFileTimeText(TimeText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = TimeText,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Time Of The Event",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            modifier = Modifier
                .padding(1.dp, 128.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TextFiledAllergensText(allergensText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = allergensText,
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
            modifier = Modifier
                .padding(1.dp, 191.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TextFiledSurveyCodeText(surveyCodeText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = surveyCodeText,
            onValueChange = textChange,
            label = {
                Text(
                    text = "Survey Code (4 Digit Code)",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            modifier = Modifier
                .padding(1.dp, 254.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}


// figur 2
@Composable
fun QuestionPageScreen(navController: NavController) {
    val selectedQuestion = remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottonGradient
                    )
                )
            )
    )
    /*
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        AddPhoto(
            modifier = Modifier
                .padding(end = 25.dp, top = 55.dp)
                .size(50.dp)
                .clickable(
                    enabled = true,
                    onClickLabel = "Clickable image",
                    onClick = {
                        navController.navigate(Screen.EventScreenEmployee.route)
                    }),
            id = R.drawable.greenconfirmedbutton
        )

    }
     */

/* //TODO ved ikke om jeg skal bruge den
    Column(modifier = Modifier
        .padding(start = 25.dp, top = 55.dp)
        .fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        OrangeBackButton(onClick = { navController.popBackStack() })
    }

 */



    Card(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 150.dp, bottom = 150.dp)
            .fillMaxWidth()
            .fillMaxSize(),

        shape = RoundedCornerShape(14.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)

    ) {  //TODO
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Text(
                text = "Create an Question",
                color = Color(0xFFB874A6),
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(start = 35.dp, top = 255.dp)

            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                onClick = { navController.navigate(Screen.EventScreenEmployee.route) },
                shape = CircleShape,
                border = BorderStroke(1.dp,color = Color.Green),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 44, green = 44, blue = 59)),
                modifier = Modifier
                    .padding(top = 420.dp)
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

        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            AddPhoto(
                modifier = Modifier
                    .padding(end = 55.dp, top = 260.dp)
                    .size(25.dp)
                    .clickable(enabled = true,
                        onClick = {
                            when (selectedQuestion.value) {
                                "Multiple-Choice Question" -> {
                                    navController.navigate(Screen.CreateMultpleChoiceQuestionScreen.route)
                                }
                                "Text Answer Question" -> {
                                    navController.navigate(Screen.CreateTextAnswerQuestionScreen.route)
                                }
                                else -> {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please Choose a Question Type",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        }),
                id = R.drawable.redaddplus
            )
        }

        DropDownMenu(selectedQuestion = selectedQuestion)
    }
}

// Figur 3
@SuppressLint("SuspiciousIndentation")
@Composable
fun CreateMultpleChoiceQuestionScreen(navController: NavController) {
    var questionText by remember { mutableStateOf("") }
    var answerText1 by remember { mutableStateOf("") }
    var answerText2 by remember { mutableStateOf("") }
    var answerText3 by remember { mutableStateOf("") }
    var answerText4 by remember { mutableStateOf("") }
    var answerText5 by remember { mutableStateOf("") }
    val checkedState = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottonGradient
                    )
                )
            )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        /*verticalArrangement = Arrangement.Center*/
    ) {
        item {
            Spacer(modifier = Modifier.size(27.dp))
            Text(
                text = "Multiple-choice",
                color = Color(0xEFFF7067),
                fontSize = 20.sp

            )
            Spacer(modifier = Modifier.size(55.dp))

            TextFiledQuestionText(questionText) { questionText = it }
            Spacer(modifier = Modifier.size(27.dp))
            TextFiledAnswerText("Answer 1", answerText1) { answerText1 = it }
            Spacer(modifier = Modifier.size(27.dp))
            TextFiledAnswerText("Answer 2", answerText2) { answerText2 = it }
            if (answerText2 != "") {
                Spacer(modifier = Modifier.size(27.dp))
                TextFiledAnswerText("Answer 3", answerText3) { answerText3 = it }
            }
            if (answerText3 != "") {
                Spacer(modifier = Modifier.size(27.dp))
                TextFiledAnswerText("Answer 4", answerText4) { answerText4 = it }
            }
            if (answerText4 != "") {
                Spacer(modifier = Modifier.size(27.dp))
                TextFiledAnswerText("Answer 5", answerText5) { answerText5 = it }
            }
            Spacer(modifier = Modifier.size(55.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
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
                onClick = {
                    val mainQuest = hashMapOf(
                        "mainQuestion" to questionText,
                        "oneChoice" to checkedState.value
                    )
                    if (answerText5 != ""){
                        val questionAnswer = hashMapOf(
                            "answer1" to answerText1,
                            "answer2" to answerText2,
                            "answer3" to answerText3,
                            "answer4" to answerText4,
                            "answer5" to answerText5
                        )
                        val subcollectionRef = db.collection("events").document(docId).collection("questions")
                            subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
                            mainQuest.set("questionId", docRef.id)
                            subcollectionRef.document(docRef.id).collection("type").document("options").set(questionAnswer)
                        }
                    }
                    else if (answerText4 != ""){
                        val questionAnswer = hashMapOf(
                            "answer1" to answerText1,
                            "answer2" to answerText2,
                            "answer3" to answerText3,
                            "answer4" to answerText4
                        )
                        val subcollectionRef = db.collection("events").document(docId).collection("questions")
                            subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
                            mainQuest.set("questionId", docRef.id)
                            subcollectionRef.document(docRef.id).collection("type").document("options").set(questionAnswer)
                        }
                    }
                    else if (answerText3 != ""){
                        val questionAnswer = hashMapOf(
                            "answer1" to answerText1,
                            "answer2" to answerText2,
                            "answer3" to answerText3
                        )
                        val subcollectionRef = db.collection("events").document(docId).collection("questions")
                            subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
                            mainQuest.set("questionId", docRef.id)
                            subcollectionRef.document(docRef.id).collection("type").document("options").set(questionAnswer)
                        }
                    }
                    else{
                    val questionAnswer = hashMapOf(
                        "answer1" to answerText1,
                        "answer2" to answerText2
                    )
                    val subcollectionRef = db.collection("events").document(docId).collection("questions")
                        subcollectionRef.add(mainQuest).addOnSuccessListener { docRef ->
                        mainQuest.set("questionId", docRef.id)
                        subcollectionRef.document(docRef.id).collection("type").document("options").set(questionAnswer)
                    }

                    }

                    navController.navigate(Screen.QuestionPageScreen.route) },
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
                onClick = { navController.popBackStack() },
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


// FIGUR 4
@Composable
fun CreateTextAnswerQuestionScreen(navController: NavController) {
    var questionText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottonGradient
                    )
                )
            )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        /*verticalArrangement = Arrangement.Center*/
    ) {
        item {
            Spacer(modifier = Modifier.size(27.dp))
            Text(
                text = "Text Answer Question",
                color = Color(0xEFFF7067),
                fontSize = 20.sp

            )
            Spacer(modifier = Modifier.size(55.dp))

            TextFiledQuestionText(questionText) { questionText = it }
            Spacer(modifier = Modifier.size(55.dp))

            Button(

                onClick = {
                    val mainQuest = hashMapOf(
                        "mainQuestion" to questionText
                    )
                    val subcollectionRef = db.collection("events").document(docId).collection("questions")
                    subcollectionRef.add(mainQuest)
                    navController.navigate(Screen.QuestionPageScreen.route) },
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
                onClick = { navController.popBackStack() },
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

@Composable
fun TextFiledQuestionText(questionText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = questionText,
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


            )
    }
}

@Composable
fun TextFiledAnswerText(text: String, answerText: String, textChange: (String) -> Unit) {
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = answerText,
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

    Column(Modifier.padding(20.dp)) {
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
