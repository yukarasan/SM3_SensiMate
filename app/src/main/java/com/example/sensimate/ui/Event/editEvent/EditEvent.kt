import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.*
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.Event.createEvent.*
import com.example.sensimate.ui.Event.createEvent.AddPhoto
import com.example.sensimate.ui.Event.editEvent.EditEventViewmodel
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.survey.Survey4
import com.example.sensimate.ui.theme.BottonGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.LightColor
import com.example.sensimate.ui.theme.RedColor
import java.util.*

/*
@Preview(showBackground = true)
@Composable
fun EditEventPreview() {
    //EditEvent()
    //EditPage()
    //EditSurvey()
    //EditSurveyPage()
}

 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditEvent(
    navController: NavController,
    eventViewModel: EventViewModel = viewModel(),
) {
    val state = eventViewModel.uiState

    val chosenEvent = eventViewModel.getEventById(state.value.chosenSurveyId)

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
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                item {
                    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
                        OrangeBackButton(onClick = { navController.navigate(Screen.EventScreenEmployee.route) }) //TODO BACK BUTTON VIRKER IKKE FOR MIG :(
                    }
                }
                item {
                    AddPhoto(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(50.dp)
                            .clickable(
                                enabled = true,
                                onClickLabel = "Clickable image",
                                onClick = { navController.navigate(Screen.EditPage.route) }),
                        /*{
                            navController.navigate(
                                Screen.EditPage.passArguments(
                                    time = time,
                                    title = title,
                                    description = description,
                                    allergens = allergens,
                                    location = location,
                                    surveyCode = surveyCode,
                                )
                            )
                        }),

                         */

                        id = R.drawable.yelloweditbutton
                    )
                }
            }
        }
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
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
                                        Title(title = chosenEvent.title)
                                        Description(
                                            description = chosenEvent.description
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Allergens(title = chosenEvent.allergens)
                        Description(description = chosenEvent.description)
                        Spacer(modifier = Modifier.size(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column() {
                                Title(title = "Location")
                            }
                            HourAndMinuteDescription(
                                hour = chosenEvent.hour,
                                minute = chosenEvent.minute
                            )
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Description(description = chosenEvent.location)

                    }
                }
                Spacer(modifier = Modifier.size(25.dp))
                /*
                Button(
                    onClick = { navController.navigate(Screen.EditSurvey.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                    modifier = Modifier.size(345.dp, 60.dp),

                    ) {
                    Text(
                        text = "Edit Survey",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = manropeFamily
                    )
                }

                 */
                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    onClick = { Database.deleteEvent(chosenEvent.title) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFFB83A3A)),
                    modifier = Modifier.size(345.dp, 60.dp),

                    ) {
                    Text(
                        text = "Delete Event",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = manropeFamily
                    )
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
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)
    )
}

@Composable
private fun HourAndMinuteDescription(hour: String, minute: String, modifier: Modifier = Modifier) {
    Text(
        text = "$hour:$minute",
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
private fun Description(description: String, modifier: Modifier = Modifier) {
    Text(
        text = description,
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
fun AddPhoto(modifier: Modifier = Modifier, id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "HEJ MED DIG ",
        modifier = modifier
    )
}

/*
@Preview
@Composable
fun EditPagePreview() {
    EditPage(navController = rememberNavController())
}

 */


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditPage(
    navController: NavController,
    //title: String,
    //time: String,
    //location: String,
    //allergens: String,
    //description: String,
    //surveyCode: String,
    eventViewModel: EventViewModel,
    editEventViewmodel: EditEventViewmodel = viewModel()
) {
    val state1 = eventViewModel.uiState

    val state = eventViewModel.uiState.collectAsState()

    val chosenEvent = eventViewModel.getEventById(eventViewModel.uiState.value.chosenSurveyId)

    Log.d("huske", eventViewModel.uiState.value.chosenSurveyId)

    val maxChar = 4


    var year: String
    var month: String
    var day: String

    Log.d("docrefagain :", chosenEvent.eventId)

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
            modifier = Modifier.padding(end = 10.dp)
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Spacer(modifier = Modifier.size(55.dp))

            TitleText(titleText = state.value.event.title,
                textChange = { eventViewModel.updateTitleString(title = it) })

            Spacer(modifier = Modifier.size(27.dp))
            DescriptionText(
                descriptionText = state.value.event.description,
                onValueChange = { eventViewModel.updateDescriptionString(description = it) })

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

                LocationText(
                    locationText = state.value.event.location,
                    onValueChange = { eventViewModel.updateLocationString(location = it) })

                AllergensText(
                    allergensText = state.value.event.allergens,
                    onValueChange = { eventViewModel.updateAllergensString(allergens = it) })


                SurveyCodeText(state.value.event.surveyCode) {
                    if (it.length <= maxChar) {eventViewModel.updateSurveyCodeString(surveyCode = it) }
                }
                EventDateChosen(
                    LocalContext.current,
                    myYear = state.value.event.year,
                    myMonth = state.value.event.month,
                    myDay = state.value.event.day,
                    onDateChange = {day, month, year ->
                        eventViewModel.updateDateString(day = day, month = month, year = year)}
                )

                Time(
                    context = LocalContext.current,
                    myHour = state.value.event.hour, myMinute = state.value.event.minute,

                    onValueChangeTime = {hour, minute -> eventViewModel.updateTime(hour = hour,
                        minute = minute)}

                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.size(75.dp))
                    Spacer(modifier = Modifier.size(250.dp))

                    Button(
                        onClick = {
                            eventViewModel.checkIfTextfieldIsEmpty(
                                context,
                                state.value.event.title,
                                state.value.event.description,
                                state.value.event.location,
                                state.value.event.year,
                                state.value.event.month,
                                state.value.event.day,
                                state.value.event.allergens,
                                state.value.event.surveyCode
                            )

                            val events = eventViewModel.createHashMapforEvent(
                                state.value.event.title,
                                state.value.event.description,
                                state.value.event.location,
                                state.value.event.year,
                                state.value.event.month,
                                state.value.event.day,
                                state.value.event.allergens,
                                state.value.event.surveyCode,
                                state.value.event.minute,
                                state.value.event.hour,
                                state.value.event.eventId
                            )

                            Log.d("documentref : ", chosenEvent.eventId)

                            Database.UpdateEvent(events, state.value.event.eventId)

                            /*
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

                                /*

                             */
                                val event = hashMapOf(
                                    "title" to titleText,
                                    "description" to descriptionText,
                                    "allergens" to allergensText,
                                    "location" to locationText,
                                    "surveyCode" to surveyCodeText,
                                    "day" to day,
                                    "month" to month,
                                    "year" to year,
                                    // "hour" to hour,
                                    // "minute" to minute,
                                    "eventId" to eventId.value
                                )

                                 */
                            //val documentID = db.collection("event").document().id

                            // Log.d("DocumentrefB4 : ", documentID)

                            Log.d("docref : ", chosenEvent.eventId)


                            /*navController.navigate(Screen.QuestionPageScreen.route)*/


                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                        modifier = Modifier.size(240.dp, 50.dp),
                        enabled = true

                    ) {

                        Text(
                            text = "Finish Editing",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = manropeFamily
                        )
                    }
                    Spacer(modifier = Modifier.size(55.dp))
                    Button(
                        onClick = {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.navigate(Screen.EventScreenEmployee.route)
                        },
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
fun Time(
    context: Context,
    myHour: String,
    myMinute: String,
    onValueChangeTime: (hour : String, minute : String) -> Unit
) {
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
        { _: TimePicker, mHour: Int, mMinute: Int ->
            mTime = "$mHour:$mMinute"
            iHour.value = mHour
            iMinute.value = mMinute
            hasChosen.value = true
            onValueChangeTime(iHour.value.toString(), iMinute.value.toString())
        }, iHour.value, iMinute.value, false

    )

    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Green,
            backgroundColor = Color.Transparent
        ),
        enabled = false,
        value = "$myHour : $myMinute",
        label = {
            Text(text = "Time Of The Event", color = Color(0xFFB874A6))
        },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 128.dp, 1.dp, 1.dp)
            .clickable { mTimePickerDialog.show() },
    )
}


@Composable
fun EventDateChosen(
    context: Context,
    myYear: String,
    myMonth: String,
    myDay: String,
    onDateChange: (day : String, month : String, year : String) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, 0)

    // Create state variables to store the selected year, month, and day
    val selectedYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val selectedMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val selectedDay = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    val hasChosen = remember {
        mutableStateOf(false)
    }

    Log.d("myyear.value", myYear)
    Log.d("mymonth.value", myMonth)
    Log.d("myday.value", myDay)


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
                onDateChange(selectedYear.value.toString(),
                    selectedMonth.value.plus(1).toString(), selectedDay.value.toString())

            }, selectedYear.value, selectedMonth.value, selectedDay.value
        )
    /*
    if (hasChosen.value) {
        myYear = selectedYear.value.toString()
        myMonth.value = (selectedMonth.value + 1).toString()
        myDay.value = selectedDay.value.toString()
    }

     */

    datePickerLog.datePicker.minDate = calendar.timeInMillis
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Green,
            backgroundColor = Color.Transparent
        ),
        enabled = false,
        value = "$myDay/$myMonth/$myYear",
        label = { Text(text = "Date For The Event", color = Color(0xFFB874A6)) },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 65.dp, 1.dp, 1.dp)
            .clickable { datePickerLog.show() },
    )
}


@Composable
fun DescriptionText(descriptionText: String, onValueChange: (String) -> Unit) {
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = descriptionText,
            onValueChange = { onValueChange(it) },
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
fun TitleText(titleText: String, textChange: (String) -> Unit) {
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = titleText,
            onValueChange = { textChange(it) },
            label = {
                Text(
                    text = "Title",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) }
        )
    }
}


@Composable
fun LocationText(locationText: String, onValueChange: (String) -> Unit) {
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = locationText,
            onValueChange = { onValueChange(it) },
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
fun AllergensText(allergensText: String, onValueChange: (String) -> Unit) {
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = allergensText,
            onValueChange = { onValueChange(it) },
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
fun SurveyCodeText(surveyCodeText: String, textChange: (String) -> Unit) {
    ContentColor1Component(contentColor = Color.White) {
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


@Composable
fun TextFiledTitleText() {
    var text by remember { mutableStateOf("Coca Cola") }
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = "Title",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            modifier = Modifier
                .padding(55.dp, 55.dp, 30.dp, 30.dp)
        )
    }
}


@Composable
fun TextFiledDescriptionText() {
    var text by remember { mutableStateOf("Come and taste the freshing sensation of Coca Cola. Get a whole six pack for free.") }
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = "Description",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            modifier = Modifier
                .padding(55.dp, 150.dp, 30.dp, 30.dp)
        )

    }
}


@Composable
fun ContentColor1Component(
    contentColor: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        content = content
    )
}


@Composable
fun TextToPhoto() {
    Text(
        text = "Edit Photo",
        color = Color(0xFFB874A6), fontSize = 11.sp,
        maxLines = 1,
        modifier = Modifier
            .padding(330.dp, 44.dp, 2.dp, 1.dp)

    )
}


@Composable
fun TextFiledLoctionText() {
    var text by remember { mutableStateOf("Helsingørmotervejen 15, 2500 lyngby") }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
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
                .padding(1.dp, 65.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun TextFiledTimeText() {
    var text by remember { mutableStateOf("??/??/?? - ??:??") }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = "Date and time",
                    color = Color(0xFFB874A6)
                )
            }, trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.yellowpencil),
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
fun EditSurvey(navController: NavController, questionViewModel: QuestionViewModel) {
    Survey4(title = "", navController, questionViewModel)
    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { navController.navigate(Screen.EditSurveyPage.route) }),
        id = R.drawable.yelloweditbutton
    )
}

@Composable
fun EditSurveyPage(navController: NavController) {
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
    Text(
        text = "Multiple-choice",
        color = Color(0xEFFF7067),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(125.dp, 30.dp, 88.dp, 269.dp)
    )
    TextFiledEditQuestionText(
        modifier = Modifier
            .padding(55.dp, 130.dp, 30.dp, 30.dp),
        "What other flavours of Coca Cola would you like?"
    )
    TextFiledEditAnswerText(
        modifier = Modifier
            .padding(55.dp, 225.dp, 30.dp, 30.dp), "Tomato"
    ) //TODO NEED MORE ANSWER FILEDS
    Divider(
        color = Color.White,
        thickness = 2.dp,
        modifier = Modifier.padding(1.dp, 400.dp, 1.dp, 1.dp)
    )
    Text(
        text = "Settings",
        color = Color(0xFFB874A6),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp, 410.dp, 88.dp, 269.dp)
    )
    Text(
        text = "Require an answer",
        color = Color(0xEFFF7067),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp, 450.dp, 88.dp, 269.dp)
    )
/*
    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ }), id = R.drawable.redgobackbutton
    )

 */
    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
        OrangeBackButton(onClick = { navController.popBackStack() }) //TODO BACK BUTTON VIRKER IKKE FOR MIG :(
    }
    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { navController.navigate(Screen.EditEvent.route) }),
        id = R.drawable.greenconfirmedbutton
    )


}

@Composable
fun TextFiledEditQuestionText(modifier: Modifier, string: String) {
    var text by remember { mutableStateOf(string) }
    com.example.sensimate.ui.Event.createEvent.ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = "Question",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            modifier = modifier

        )
    }
}

@Composable
fun TextFiledEditAnswerText(modifier: Modifier, string: String) {
    var text by remember { mutableStateOf(string) }
    com.example.sensimate.ui.Event.createEvent.ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = "Answer",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = { Text(text = "Type here...", color = Color(0xEFFF7067)) },
            modifier = modifier

        )
    }
}
