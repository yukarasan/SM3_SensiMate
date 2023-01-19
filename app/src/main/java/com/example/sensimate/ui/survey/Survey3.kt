package com.example.sensimate.ui.survey


import android.annotation.SuppressLint
import android.util.Log
import com.example.sensimate.ui.appcomponents.composable.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow

//@Preview(showBackground = true)

/**
 * @author Anshjyot Singh
 * Displays a survey with a title, question, and form for collecting information. Includes an orange back button and linear progress indicator.
 * Allows for selection of options and collection of answers.
 * @param title The title of the survey
 * @param navController The navigation controller for handling navigation
 * @param questionViewModel The view model that holds the current question and options
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Survey3(title: String, navController: NavController, questionViewModel: QuestionViewModel,
            progress: MutableStateFlow<Float>
) {
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 0.dp, top = 10.dp)
    ) {
        items(1) {
            OrangeBackButton({navController.popBackStack()})
        }
        items(1) {
            ProgressPreview(progress = questionViewModel.progress.value)
        }
        items(1) {
            Question(questionViewModel)
        }
        items(1) {
            SurveyTitle(title)
        }
        items(1) {
            SurveyImage()
        }
        items(1) {
            Information3(questionViewModel)
        }
        items(1) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
            ) {
                //PreviousButton(onClick = { navController.navigate(Screen.Survey2.route) } )
                //NextButton(onClick = { navController.navigate(Screen.Survey4.route) } )
            }
        }
    }
}

/*
@Composable
private fun ProgressPreview(progress: Float) {
    LinearProgressIndicator(
        modifier = Modifier
            .padding(top = 20.dp, start = 0.dp)
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(15.dp)),
        backgroundColor = darkpurple,
        color = lightpurple, //progress color
        progress = progress //0.75f //TODO:  Needs state hoisting in future.
    )
}
 */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Information3(questionViewModel: QuestionViewModel) {
    var selectedOption by remember { mutableStateOf(0) }

    val options = questionViewModel.uiState.value.currentQuestion.options
    var listener: ((option: Int, value: Boolean) -> Unit)? = { i: Int, b: Boolean ->
        selectedOption = i

        questionViewModel.addAnswer(options[i])
        //questionViewModel.setAnswer(options)
        //questionViewModel.addQuestion(questionTitle = questionViewModel.uiState.value.currentQuestion.mainQuestion, answers = mutableListOf(options[i]), onechoice = true)


        Log.d("Test2", options.toString())
    }

    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 0.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SurveyImage2()
                SurveyImage3()
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, option ->
                    Information1(title = option)
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, _ ->
                    RoundedCheckView(listener, selectedOption, option = index)


                    //Log.d("Test2", options.toString())
                }
            }
        }
    }
}





@Composable
private fun SurveyImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.drinkingdude) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(320.dp)
            .clip(RoundedCornerShape(20))
            .fillMaxWidth(),


        )
}

@Composable
private fun SurveyImage2(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.thumbsdown) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .padding(start = 5.dp, top = 10.dp)
    )
}

@Composable
private fun SurveyImage3(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.heart) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .padding(top = 10.dp, end = 10.dp)
    )
}


@Composable
private fun Information1(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 5.dp, start = 20.dp, end = 20.dp)
    )
}


