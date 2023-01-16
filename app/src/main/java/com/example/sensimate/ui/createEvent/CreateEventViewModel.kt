package com.example.sensimate.ui.createEvent

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class CreateEventUistate(
    var titleText: MutableState<String> = mutableStateOf(""),
    var descriptionText: MutableState<String> = mutableStateOf(""),
    var locationText: MutableState<String> = mutableStateOf(""),
    var allergensText: MutableState<String> = mutableStateOf(""),
    var surveyCodeText: MutableState<String> = mutableStateOf(""),
    val myYear: MutableState<String> =  mutableStateOf(""),
    val myMonth: MutableState<String> =  mutableStateOf(""),
    val myDay: MutableState<String> = mutableStateOf(""),
    val myHour: MutableState<String> = mutableStateOf(""),
    val myMinute: MutableState<String> = mutableStateOf(""),
   // val eventCode : MutableState<String> = mutableStateOf("")
)
class CreateEventViewModel: ViewModel(){
   val  _uistate = MutableStateFlow(CreateEventUistate())
    val uistate: StateFlow<CreateEventUistate> = _uistate.asStateFlow()



    fun check(context: Context, navController: NavController) {
        if (uistate.value.titleText.value == "") {
            Toast.makeText(
                context,
                "Title was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.descriptionText.value == "") {
            Toast.makeText(
                context,
                "Description was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.locationText.value == "") {
            Toast.makeText(
                context,
                "Location was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.myYear.value == "") {
            Toast.makeText(
                context,
                "Date was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.myHour.value == "") {
            Toast.makeText(
                context,
                "Time was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.allergensText.value == "") {
            Toast.makeText(
                context,
                "Allergens was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.surveyCodeText.value == "") {
            Toast.makeText(
                context,
                "SurveyCode was not entered",
                Toast.LENGTH_SHORT
            ).show()
        } else if (uistate.value.surveyCodeText.value.length < 4) {
            Toast.makeText(
                context,
                "Please enter a 4 digit SurveyCode",
                Toast.LENGTH_SHORT
            ).show()
        }
        else {
            Database.createEvent(
                title = uistate.value.titleText.value,
                description = uistate.value.descriptionText.value,
                allergens = uistate.value.allergensText.value,
                location = uistate.value.locationText.value,
                surveyCode = uistate.value.surveyCodeText.value,
                day = uistate.value.myDay.value,
                month = uistate.value.myMonth.value,
                year = uistate.value.myYear.value,
                hour = uistate.value.myHour.value,
                minute = uistate.value.myMinute.value,
            )
            navController.navigate(Screen.QuestionPageScreen.route)
            _uistate.value = _uistate.value.copy(titleText = mutableStateOf(""),
                descriptionText = mutableStateOf(""), allergensText = mutableStateOf(""),
                locationText = mutableStateOf(""), surveyCodeText = mutableStateOf(""),
                myDay = mutableStateOf(""), myMonth = mutableStateOf(""), myYear = mutableStateOf(""),
                myHour = mutableStateOf(""), myMinute = mutableStateOf("")
            )
        }
    }
}
