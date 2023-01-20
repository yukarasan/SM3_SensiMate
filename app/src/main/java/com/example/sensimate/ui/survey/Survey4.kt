package com.example.sensimate.ui.survey


import android.annotation.SuppressLint
import android.util.Log
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
//import com.example.sensimate.data.questionandsurvey.MyAnswer
//import com.example.sensimate.data.Database.updateSurvey
import com.example.sensimate.data.QuestionandSurvey.QuestionViewModel
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Anshjyot Singh
 * A composable function that displays a survey with a title, a question, and a form for collecting information. It displays an orange back button that when pressed, pops the backstack of the passed NavController. The layout is filled with a dark purple and bottom gradient color, a progress bar, and a text title.
 * @param title the title of the survey
 * @param navController the NavController for the back button
 * @param questionViewModel the view model for the survey question and options
 */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Survey4(title: String, navController: NavController, questionViewModel: QuestionViewModel, progress: MutableStateFlow<Float>) {
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
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        OrangeBackButton { navController.popBackStack() }
       // ProgressPreview(1f)
        ProgressPreview(progress = questionViewModel.progress.value)
        Question(questionViewModel)
        SurveyTitle(title)
        Information4(questionViewModel)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
        ) {

        }
    }

}





@SuppressLint("StateFlowValueCalledInComposition", "MutableCollectionMutableState")
@Composable
fun Information4(questionViewModel: QuestionViewModel) {
    val checkedState = remember { mutableStateOf(false) }
    val options = questionViewModel.uiState.value.currentQuestion.options
    val answer = remember { mutableListOf<String>() }


    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 25.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CheckBox(questionViewModel, option = answer, option)
                    Option(title = option)
                    Spacer(modifier = Modifier.width((120.dp)))
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 0.dp),
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                    },
                    colors = CheckboxDefaults
                        .colors(
                            uncheckedColor = GreyColor,
                            checkmarkColor = lightpurple,
                            checkedColor = lightpurple,
                            disabledColor = darkbluegrey,
                            disabledIndeterminateColor = GreyColor,
                        )
                )
                Option(title = "Other: ")
                Spacer(modifier = Modifier.width((60.dp)))
                var other by remember { mutableStateOf("") }
                MyTextField(
                    text = other,
                    textSize = 10,
                    onValueChange = { other = it },
                    placeHolder = "",
                    width = 100,
                    height = 20,
                    keyboardType = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None,
                    myTextColor = Color.White,
                    backgroundColor = GreyColor,
                    placeHolderColor = Color.White
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
            .padding(top = 0.dp, start = 20.dp, bottom = 5.dp)
    )
}


@Composable
fun CheckBox(questionViewModel: QuestionViewModel, option: MutableList<String>, options: String) {
    val checkedState = remember { mutableStateOf(false) }

    //if (isChecked.value) {
    Checkbox(
        modifier = Modifier
            .padding(start = 0.dp, top = 0.dp),
        checked = checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            option.add(options)
            questionViewModel.addAnswer(option.toString())

            Log.d("Test1", options)
        },
        colors = CheckboxDefaults
            .colors(
                uncheckedColor = GreyColor,
                checkmarkColor = lightpurple,
                checkedColor = lightpurple,

                disabledColor = darkbluegrey,
                disabledIndeterminateColor = GreyColor,
            )
    )
}


@Composable
fun FinishButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(PurpleButtonColor),
        modifier = Modifier
            .height(38.dp)
            .width(130.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                "Finish",
                color = Color.White,
                fontSize = 15.sp,
            )


            Image(
                painter = painterResource(id = R.drawable.tick),
                contentDescription = "Finish",
                colorFilter = ColorFilter.tint(Color.White)


            )

        }

    }


}