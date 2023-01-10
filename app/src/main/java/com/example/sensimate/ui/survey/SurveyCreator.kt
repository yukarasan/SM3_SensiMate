package com.example.sensimate.ui.survey

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.Pager
import com.example.sensimate.data.EventViewModel
import com.example.sensimate.data.questionandsurvey.MyQuestion
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.ui.navigation.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun SurveyCreator(
    navController: NavController,
    questionViewModel: QuestionViewModel,
    eventViewModel: EventViewModel
) {
    val surveyId = eventViewModel.uiState.value.chosenSurveyId
    val state = questionViewModel.uiState.value
    var hasOther: Boolean = false

    // Returns a scope that's cancelled when F is removed from composition
    val coroutineScope = rememberCoroutineScope()
    val loaded = remember {
        mutableStateOf(false)
    }
    val loaded2 = remember {
        mutableStateOf(false)
    }

    if (loaded2.value) {
        Log.d("sjdj", "dk")
        AllPages(navController,questionViewModel.uiState.value.questions, questionViewModel)
    }

    LaunchedEffect(key1 = true) {
        if (!loaded.value) {
            loaded.value = true


            questionViewModel
                .insertQuestions(
                    questionViewModel.uiState.value, surveyId
                )

            loaded2.value = true




            Log.d("in scope", questionViewModel.uiState.value.questions.size.toString())
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AllPages(
    navController: NavController,
    questions: List<MyQuestion>,
    questionViewModel: QuestionViewModel,
    /*eventViewModel: EventViewModel*/
) {
    val answers = mutableListOf<String>() //i vm

    HorizontalPager(
        count = questions.size,
        modifier = Modifier
    ) { questionIndex ->

           // for (question in questions) {
                //questions[questionIndex]

                for (option in questions[questionIndex].options) {
                    answers.add(option)

                    questionViewModel.setCurrentQuestion(questions[questionIndex])


                    if (!questions[questionIndex].oneChoice) {

                        Survey4(title = questions[questionIndex].mainQuestion, navController = navController, questionViewModel)
                    }

                    else if (questions[questionIndex].oneChoice) {

                        Survey2(title = questions[questionIndex].mainQuestion, navController = navController, questionViewModel)

                    } else if (questions[questionIndex].oneChoice2) {

                        Survey3(title = questions[questionIndex].mainQuestion, navController = navController, questionViewModel)

                    } else {
                        Survey(
                            title = questions[questionIndex].mainQuestion,
                            navController = navController
                        )

                    }


                }
            }

        }








