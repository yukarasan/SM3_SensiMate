package com.example.sensimate.ui.Event.createEvent

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.R
import com.example.sensimate.data.db
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.*
import java.util.*

/*
@Preview(showBackground = true)
@Composable
fun CreateEventPreview() {
    //CreateEventScreen()
    QuestionPageScreen()
    //CreateMultpleChoiceQuestionScreen()
}
*/


@Composable
fun CreateEventScreen(navController: NavController){
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("") }
    var allergensText by remember { mutableStateOf("") }
    var surveyCodeText by remember { mutableStateOf("") }
    val myYear = remember { mutableStateOf("") }
    val myMonth = remember { mutableStateOf("") }
    val myDay = remember { mutableStateOf("") }
    val maxChar = 4
    var year: String
    var month: String
    var day: String
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

    AddPhoto(modifier = Modifier
        .padding(345.dp, 20.dp, 2.dp, 1.dp)
        .size(20.dp), id = R.drawable.ic_add_circle_outlined
    )
    TextToPhoto()
    LazyColumn(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

   item { Spacer(modifier = Modifier.size(55.dp))
       TextFiledTitleText(titleText) { titleText = it }
       Spacer(modifier = Modifier.size(27.dp))
      TextFiledDescriptionText(descriptionText) { descriptionText = it }
       Spacer(modifier = Modifier.size(55.dp))
      Card(
        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xFF4D3B72)


    ){

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
        TextFiledLocationText(locationText) {locationText = it}
        TextFiledAllergensText(allergensText) {allergensText = it }
       TextFiledSurveyCodeText(surveyCodeText) {if (it.length <= maxChar) surveyCodeText = it }

          ChooseBirthDate(
              LocalContext.current,
              myYear = myYear,
              myMonth = myMonth,
              myDay = myDay
          )
          day = myDay.value
          month = myMonth.value
        year = myYear.value


    Column(

        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.size(75.dp))
        Spacer(modifier = Modifier.size(250.dp))

    Button(
        onClick = {val event = hashMapOf(
                                "title" to titleText,
                                "description" to descriptionText,
                                "allergens" to allergensText,
                                "location" to locationText,
                                "surveyCode" to surveyCodeText,
                                "day" to day,
                                "month" to month,
                                "year" to year
                                                )
            db.collection("TESTER").add(event)
                  /*navController.navigate(Screen.QuestionPageScreen.route)*/},
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
        onClick = {navController.navigate(Screen.EventScreenEmployee.route)},
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
    }}}}

@Composable
fun ChooseBirthDate(
    context: Context,
    myYear: MutableState<String>,
    myMonth: MutableState<String>,
    myDay: MutableState<String>,
) {
    val calendar = Calendar.getInstance()
    //calendar.add(Calendar.YEAR, -18)

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

    //datePickerLog.datePicker.maxDate = calendar.timeInMillis
        TextField(
            colors = TextFieldDefaults.textFieldColors(textColor = Color.Green,backgroundColor = Color.Transparent),
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
fun TextFiledTitleText(titleText: String, textChange: (String) -> Unit){
        ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = titleText,
            onValueChange =  textChange,
            label = {
                Text(
                    text = "Title",
                    color = Color(0xFFB874A6)
                )
                }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )}
            )
    }
}

@Composable
fun TextFiledDescriptionText(descriptionText: String, textChange: (String) -> Unit){
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
                placeholder = {Text(text = "Type here...",color = Color(0xEFFF7067))}
            )

        }
}

@Composable
fun ContentColorComponent(
    contentColor: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides contentColor,
        content = content)
}



@Composable
fun AddPhoto(modifier: Modifier = Modifier,id: Int){
    IconButton(onClick = { /*TODO*/ }) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "HEJ MED DIG ",
            modifier = modifier
        )
    }
}
@Composable
fun TextToPhoto(){
    Text(text = "Add Photo",
    color = Color(0xFFB874A6), fontSize = 11.sp,
        maxLines = 1,
    modifier = Modifier
        .padding(330.dp, 44.dp, 2.dp, 1.dp)

    )
    }


@Composable
fun TextFiledLocationText(locationText: String, textChange: (String) -> Unit){
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
                            contentDescription = "")

                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                singleLine = true,

                placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = Modifier
                .padding(1.dp, 2.dp, 1.dp, 1.dp)
                .fillMaxWidth()
            )
        }
}



@Composable
fun TextFileDistanceText(distanceText: String, textChange: (String) -> Unit){
        ContentColorComponent(contentColor = Color.White) {
            TextField(
                value = distanceText,
                onValueChange = textChange,
                label = {
                    Text(
                        text = "Distance To Event",
                        color = Color(0xFFB874A6)
                    )
                }, trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.yellowpencil),
                            modifier = Modifier
                                .size(20.dp),
                            contentDescription = "")

                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                singleLine = true,

                placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
                modifier = Modifier
                    .padding(1.dp, 2.dp, 1.dp, 1.dp)
                    .fillMaxWidth()
                   )
        }
}

@Composable
fun TextFiledAllergensText(allergensText: String,textChange: (String) -> Unit){
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

            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = Modifier
                .padding(1.dp, 128.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TextFiledSurveyCodeText(surveyCodeText: String, textChange: (String) -> Unit){
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = surveyCodeText,
            onValueChange = textChange,
            label = {
                Text(
                    text = "4 Digit Code",
                    color = Color(0xFFB874A6)
                )
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,

            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = Modifier
                .padding(1.dp, 191.dp, 1.dp, 1.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun TextFiledQuestionText(modifier: Modifier,string: String){
    var text by remember { mutableStateOf(string) }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText },
            label = {
                Text(
                    text = "Question",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = modifier

        )
    }
}

@Composable
fun TextFiledAnswerText(modifier: Modifier,string: String){
    var text by remember { mutableStateOf(string) }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText },
            label = {
                Text(
                    text = "Answer",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = modifier

        )

    }
}

// figur 2

@Composable
fun QuestionPageScreen(navController: NavController){
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

    Card(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 150.dp, bottom = 150.dp)
            .fillMaxWidth()
            .fillMaxSize(),

        shape = RoundedCornerShape(14.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)

    ){} //TODO
            Text(
                text = "Create your first question",
                color = Color(0xFFB874A6),
                fontSize = 20.sp,
                modifier = Modifier
                    //.padding(40.dp, 250.dp, 88.dp, 269.dp)
                    .padding(60.dp, 400.dp, 88.dp, 269.dp)
            )
            AddPhoto(modifier = Modifier
                .padding(300.dp, 405.dp, 1.dp, 1.dp)
                .size(20.dp)
                .clickable(enabled = true,
                    onClick = { navController.navigate(Screen.CreateMultpleChoiceQuestionScreen.route) }),
                id = R.drawable.redaddplus)

    /*
    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ })
        ,id = R.drawable.redgobackbutton)

     */
    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
        OrangeBackButton(onClick = {navController.popBackStack()}) //TODO BACK BUTTON VIRKER IKKE FOR MIG :(
    }

    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { navController.navigate(Screen.EventScreenEmployee.route) })
        , id = R.drawable.greenconfirmedbutton)


}

// Figur 3
@Composable
fun CreateMultpleChoiceQuestionScreen(navController: NavController){
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
            .padding(125.dp, 30.dp, 88.dp, 269.dp))
    TextFiledQuestionText(modifier = Modifier
        .padding(55.dp, 130.dp, 30.dp, 30.dp),"")
    TextFiledAnswerText(modifier = Modifier
        .padding(55.dp, 225.dp, 30.dp, 30.dp),"")
    Divider(
        color = Color.White,
        thickness = 2.dp,
        modifier = Modifier.padding(1.dp,400.dp, 1.dp, 1.dp))
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

//TODO LAV EN GO BACK BUTTON
    /*
    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ })
        , id = R.drawable.redgobackbutton)

 */
    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
        OrangeBackButton(onClick = {navController.popBackStack()}) //TODO BACK BUTTON VIRKER IKKE FOR MIG :(
    }


    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { navController.navigate(Screen.EventScreenEmployee.route) })
        , id = R.drawable.greenconfirmedbutton)


}



