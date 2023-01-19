import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.*

import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import com.example.sensimate.ui.createEvent.AddPhoto
import com.example.sensimate.ui.createEvent.ContentColorComponent
import com.example.sensimate.ui.createEvent.TextToPhoto
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.survey.Survey4
import com.example.sensimate.ui.theme.*
import java.util.*


/**
 * This class represents the Edit Event Screen, this screen contains the UI for editing an event,
 * here we are passing arguments such as;
 * @param navController: NavController, to handle navigation between screens.
 * @param eventViewModel: EventViewModel, this viewmodels handles the logic of this EditEvent Screen.
 * @param questionViewModel: QuestionViewModel, the viewmodel for handling the questions in the survey.
 * @author Sabirin Omar
 */


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditEvent(
    navController: NavController,
    eventViewModel: EventViewModel = viewModel(),
    questionViewModel: QuestionViewModel
) {

    val eventState = eventViewModel.uiState
    val chosenEvent = eventViewModel.getEventById(eventState.value.chosenSurveyId)
    val state = eventViewModel.uiState.collectAsState()
    //val context2 = remember { requireContext() }
    var showFieldAlert by remember { mutableStateOf(false) }
    var showSecondFieldAlert by remember { mutableStateOf(false) }
    var showConfirmation by remember { mutableStateOf(false) }

    val context = LocalContext.current

    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) -> {
            // You can use the API that requires the permission.
        }
        else -> {
            // You can directly ask for the permission.
            requestPermissions(
                context as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }

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
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        LazyColumn() {
            item {
                LazyRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        Column(modifier = Modifier.padding(5.dp, 5.dp)) {
                            OrangeBackButton(onClick = { navController.navigate(Screen.EventScreenEmployee.route) })
                        }
                    }
                    item {
                        AddPhoto(
                            modifier = Modifier
                                .padding(5.dp)
                                .size(50.dp)
                                .clickable(
                                    enabled = true,
                                    onClickLabel = "",
                                    onClick = { navController.navigate(Screen.EditPage.route) }),

                            id = R.drawable.yelloweditbutton
                        )
                    }
                }
            }
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

                        Spacer(Modifier.size(15.dp))

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
                                Time(
                                    hour = chosenEvent.hour,
                                    minute = chosenEvent.minute
                                )
                                Address(address = chosenEvent.location)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = {
                            showConfirmation = true
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(Color(0xFFB83A3A)),
                        modifier = Modifier.size(240.dp, 50.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.deleteEvent),
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            color = Color.White,
                            fontFamily = manropeFamily
                        )
                    }
                    if (showConfirmation) {

                        AlertDialog(
                            onDismissRequest = {
                                showConfirmation = false
                            },
                            title = {
                                Text(text = stringResource(id = R.string.confirmDeletion))
                            },
                            text = {
                                Text(text = stringResource(id = R.string.deleteEventSure))
                            },
                            confirmButton = {
                                Button(

                                    onClick = {
                                        Database.deleteEvent(chosenEvent.title)

                                        navController.navigate(Screen.EventScreenEmployee.route) {
                                            popUpTo(Screen.EventScreenEmployee.route) {
                                                inclusive = true
                                            }
                                            navController.clearBackStack(Screen.EditEvent.route)
                                        }

                                        showConfirmation = false

                                    }) {
                                    Text(stringResource(id = R.string.deleteEvent))
                                }
                            },
                            dismissButton = {
                                Button(

                                    onClick = {
                                        showConfirmation = false
                                    }) {
                                    Text(stringResource(id = R.string.cancel))
                                }
                            }
                        )
                    }
                    if (getBooleanFromLocalStorage(
                            stringResource(id = R.string.isAdmin),
                            context = context
                        )
                    ) {

                        Spacer(modifier = Modifier.size(25.dp))
                        Button(
                            onClick = {
                                questionViewModel.updateAnswer(
                                    chosenEvent.eventId, context = context,
                                    boolean = true
                                )
                                navController.navigate(Screen.EditEvent.route)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32)),
                            modifier = Modifier.size(240.dp, 50.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.extractExcel),
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
    }
}

/**
 * This composable displays the title of the EditEvent Screen
 * @author Sabirin Omar
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
 * This composable displays the description of the EditEvent Screen.
 * @author Sabirin Omar
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
 * This composable displays the time of the event in EditEvent Screen.
 * @author Sabirin Omar
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
 * This composable displays the where the event is located in EditEvent Screen.
 * @author Sabirin Omar
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
 * This composable displays a description if there is allergens in the event in EditEvent Screen.
 * @author Sabirin Omar
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
 * This class represents the Edit Page Screen, this screen contains the UI for editing a page of an event
 * @param navController: NavController, to handle navigation between screens
 * @param eventViewModel: EventViewModel, the viewmodel for handling events
 * @author Sabirin Omar
 */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditPage(
    navController: NavController,
    eventViewModel: EventViewModel,
) {

    val state = eventViewModel.uiState.collectAsState()
    val chosenEvent = eventViewModel.getEventById(eventViewModel.uiState.value.chosenSurveyId)

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
                    if (it.length <= maxChar) {
                        eventViewModel.updateSurveyCode(surveyCode = it)
                    }
                }
                EventDateChosen(
                    LocalContext.current,
                    myYear = state.value.event.year,
                    myMonth = state.value.event.month,
                    myDay = state.value.event.day,
                    onDateChange = { day, month, year ->
                        eventViewModel.updateDateString(day = day, month = month, year = year)
                    }
                )

                Time(
                    context = LocalContext.current,
                    myHour = state.value.event.hour, myMinute = state.value.event.minute,

                    onValueChangeTime = { hour, minute ->
                        eventViewModel.updateTime(
                            hour = hour,
                            minute = minute
                        )
                    }

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
                            eventViewModel.checkIfTextfieldIsEmpty(context, navController)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
                        modifier = Modifier.size(240.dp, 50.dp),
                        enabled = true

                    ) {

                        Text(
                            text = stringResource(id = R.string.finishEditing),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = manropeFamily
                        )
                    }
                    Spacer(modifier = Modifier.size(55.dp))
                    Button(
                        onClick = {
                            navController.navigate(Screen.EventScreenEmployee.route) {
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedColor),
                        modifier = Modifier.size(240.dp, 50.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.goBack),
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

/**
 * This composable displays a changeable textField which indicates the time of the event, in EditPage.
 * @author Sabirin Omar
 */

@Composable
fun Time(
    context: Context,
    myHour: String,
    myMinute: String,
    onValueChangeTime: (hour: String, minute: String) -> Unit
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
            Text(text = stringResource(id = R.string.timeOfEvent), color = Color(0xFFB874A6))
        },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 128.dp, 1.dp, 1.dp)
            .clickable { mTimePickerDialog.show() },
    )
}

/**
 * This composable displays a changeable textField which indicates the date of the event, in EditPage.
 * @author Sabirin Omar
 */

@Composable
fun EventDateChosen(
    context: Context,
    myYear: String,
    myMonth: String,
    myDay: String,
    onDateChange: (day: String, month: String, year: String) -> Unit
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
                onDateChange(
                    selectedYear.value.toString(),
                    selectedMonth.value.plus(1).toString(), selectedDay.value.toString()
                )

            }, selectedYear.value, selectedMonth.value, selectedDay.value
        )

    datePickerLog.datePicker.minDate = calendar.timeInMillis
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Green,
            backgroundColor = Color.Transparent
        ),
        enabled = false,
        value = "$myDay/$myMonth/$myYear",
        label = {
            Text(
                text = stringResource(id = R.string.dateOfEvent),
                color = Color(0xFFB874A6)
            )
        },
        onValueChange = {},
        modifier = Modifier
            .padding(1.dp, 65.dp, 1.dp, 1.dp)
            .clickable { datePickerLog.show() },
    )
}

/**
 * This composable displays a changeable description textField in EditPage.
 * @author Sabirin Omar
 */


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DescriptionText(descriptionText: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = descriptionText,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.description),
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )

    }
}

/**
 * This composable displays a changeable title textField in EditPage.
 * @author Sabirin Omar
 */


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TitleText(titleText: String, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = titleText,
            onValueChange = { textChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.Title),
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )
    }
}

/**
 * This composable displays a changeable location textField in EditPage.
 * @author Sabirin Omar
 */


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocationText(locationText: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = locationText,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.location),
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }),

            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },
            modifier = Modifier
                .padding(1.dp, 2.dp, 1.dp, 1.dp)
                .fillMaxWidth()

        )
    }
}

/**
 * This composable displays a changeable allergens textField in EditPage.
 * @author Sabirin Omar
 */


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AllergensText(allergensText: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = allergensText,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.allergens),
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),

            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },
            modifier = Modifier
                .padding(1.dp, 191.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

/**
 * This composable displays a changeable surveyCode textField in EditPage.
 * @author Sabirin Omar
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SurveyCodeText(surveyCodeText: String, textChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ContentColor1Component(contentColor = Color.White) {
        TextField(
            value = surveyCodeText,
            onValueChange = textChange,
            label = {
                Text(
                    text = stringResource(id = R.string.surveyCode),
                    color = Color(0xFFB874A6)
                )
            },

            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },

            modifier = Modifier
                .padding(1.dp, 254.dp, 1.dp, 1.dp)
                .fillMaxWidth()
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

//For later use, so that we can edit the survey

/**
 * EditSurveyPage is a screen that is made so that we can edit a survey, if this is needed as an
 * employee, but because of the limited time this feature is not furfilled, but could be done for
 * later use. Also for later use, this composable would take in as argument a viewmodel, which
 * contains the logic of this composable.
 * @param navController: NavController, to handle navigation between screens.
 * @author Sabirin Omar
 */

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
        text = stringResource(id = R.string.multipleChoice),
        color = Color(0xEFFF7067),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(125.dp, 30.dp, 88.dp, 269.dp)
    )
    TextFiledEditQuestionText(
        modifier = Modifier
            .padding(55.dp, 130.dp, 30.dp, 30.dp),
        stringResource(id = R.string.flavourQuestion)
    )
    TextFiledEditAnswerText(
        modifier = Modifier
            .padding(55.dp, 225.dp, 30.dp, 30.dp), stringResource(id = R.string.tomato)
    ) //TODO NEED MORE ANSWER FILEDS
    Divider(
        color = Color.White,
        thickness = 2.dp,
        modifier = Modifier.padding(1.dp, 400.dp, 1.dp, 1.dp)
    )
    Text(
        text = stringResource(id = R.string.settings),
        color = Color(0xFFB874A6),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp, 410.dp, 88.dp, 269.dp)
    )
    Text(
        text = stringResource(id = R.string.reqAnswer),
        color = Color(0xEFFF7067),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp, 450.dp, 88.dp, 269.dp)
    )

    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = stringResource(id = R.string.clickImage),
                onClick = { /*TODO*/ }), id = R.drawable.redgobackbutton
    )


    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
        OrangeBackButton(onClick = {
            navController.navigate(Screen.EventScreenEmployee.route) {
                popUpTo(Screen.EditEvent.route) {
                    inclusive = true
                }
            }
        })
    }
    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = stringResource(id = R.string.clickImage),
                onClick = { navController.navigate(Screen.EditEvent.route) }),
        id = R.drawable.greenconfirmedbutton
    )
}


/**
 * Again for later use, this composable EditSurvey has a button which when clicked should navigate
 * you to EditSurvey Screen.
 * @author Sabirin Omar
 */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditSurvey(navController: NavController, questionViewModel: QuestionViewModel) {
    Survey4(title = "", navController, questionViewModel, questionViewModel.progress)
    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = stringResource(id = R.string.clickImage),
                onClick = { navController.navigate(Screen.EditSurveyPage.route) }),
        id = R.drawable.yelloweditbutton
    )
}


/**
 * Again for later use, this composable TextFiledEditQuestionText is created for editSurvey.
 * @author Sabirin Omar
 */


@Composable
fun TextFiledEditQuestionText(modifier: Modifier, string: String) {
    var text by remember { mutableStateOf(string) }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = stringResource(id = R.string.question),
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },
            modifier = modifier

        )
    }
}

/**
 * Again for later use, this composable TextFiledEditAnswerText is created for editSurvey.
 * @author Sabirin Omar
 */

@Composable
fun TextFiledEditAnswerText(modifier: Modifier, string: String) {
    var text by remember { mutableStateOf(string) }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(
                    text = stringResource(id = R.string.answer),
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type),
                    color = Color(0xEFFF7067)
                )
            },
            modifier = modifier

        )
    }
}
