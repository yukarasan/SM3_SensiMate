package com.example.sensimate.ui.survey

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.data.Database.updateSurvey
import com.example.sensimate.data.EventViewModel
//import com.example.sensimate.data.questionandsurvey.MyAnswer
//import com.example.sensimate.data.questionandsurvey.MyAnswer
//import com.example.sensimate.data.questionandsurvey.MyAnswer2
import com.example.sensimate.data.questionandsurvey.MyQuestion
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun SurveyCreator(
    navController: NavController,
    questionViewModel: QuestionViewModel,
    eventViewModel: EventViewModel

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
    {
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val showLoading = remember {
            mutableStateOf(true)
        }
        showLoadingSurvey(showLoading)
    }



    val surveyId = eventViewModel.uiState.value.chosenSurveyId
    val state = questionViewModel.uiState.value
    var hasOther: Boolean = false



    // Returns a scope that's cancelled when F is removed from composition

    val loaded = remember {
        mutableStateOf(false)
    }
    val loaded2 = remember {
        mutableStateOf(false)
    }

    if (loaded2.value) {
        Log.d("sjdj", "dk")
        AllPages(navController, questionViewModel.uiState.value.questions, questionViewModel, surveyId)
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
/*
private fun nextQuestion(questionViewModel: QuestionViewModel) {
    questionViewModel.setAnswer(MyAnswer(myAnswer = selectedAnswers, mainQuestion = questionViewModel.uiState.value.currentQuestion.mainQuestion))
    pager.nextPage()
}

 */

val selectedAnswers = mutableStateOf<List<String>>(emptyList())

/*
private fun nextQuestion(questionViewModel: QuestionViewModel) {
    questionViewModel.setCurrentQuestion(questionViewModel.uiState.value.currentQuestion, selectedAnswers)
    pager.nextPage()
}

 */


@Composable
fun showLoadingSurvey(showloading: MutableState<Boolean>) {
    if (showloading.value) {
        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(50.dp))
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AllPages(
    navController: NavController,
    questions: List<MyQuestion>,
    questionViewModel: QuestionViewModel,
    eventId: String

) {
    val answers = mutableListOf<String>() //i vm

    //val selectedAnswers = remember { mutableStateOf(listOf<MyAnswer>()) }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()



    HorizontalPager(
        count = questions.size,
        modifier = Modifier,
        state = pagerState
    ) { questionIndex ->
        // for (question in questions) {
        //questions[questionIndex]


        for (option in questions[questionIndex].options) {
            answers.add(option)

            questionViewModel.setCurrentQuestion(questions[questionIndex])


            if (!questions[questionIndex].oneChoice) {

                Survey4(
                    title = questions[questionIndex].mainQuestion,
                    navController = navController,
                    questionViewModel

                )



            } else if (questions[questionIndex].oneChoice) {

                Survey2(
                    title = questions[questionIndex].mainQuestion,
                    navController = navController,
                    questionViewModel
                )


            } else if (questions[questionIndex].oneChoice2) {

                Survey3(
                    title = questions[questionIndex].mainQuestion,
                    navController = navController,
                    questionViewModel
                )


            } else {
                Survey(
                    title = questions[questionIndex].mainQuestion,
                    navController = navController
                )

            }


        }

        Spacer(modifier = Modifier.size(600.dp))
        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
        ) {

            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage > 0) {
                    PreviousButton(onClick = {
                        if (pagerState.currentPage > 0) {

                            scope.launch {
                                //PreviousButton(onClick = {
                                pagerState.currentPage
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    })
                }

                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    FinishButton(onClick = {
                        navController.navigate(Screen.EventScreen.route)
                        //questionViewModel.updateAnswer(eventId)
                        questionViewModel.updateAnswer(eventId)
                    })
                } else {
                    Row(  modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End) {
                        NextButton(onClick = {
                            if (pagerState.currentPage < pagerState.pageCount - 1) {
                                scope.launch {
                                    //questionViewModel.setAnswer(selectedAnswers.value)
                                    //questionViewModel.setAnswer()
                                    //PreviousButton(onClick = {
                                    pagerState.currentPage
                                    pagerState.scrollToPage(pagerState.currentPage + 1)

                                }
                            }
                        }

                        )

                    }


                }
            }
        }
    }
}

























