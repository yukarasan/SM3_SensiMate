
package com.example.sensimate.data.QuestionandSurvey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context
//import com.example.sensimate.ExcelDownloader

/**
 * @author Anshjyot Singh
 * QuestionViewModel is a ViewModel class that holds the state of questions and answers.
 * It contains the following methods:
 * insertQuestions: A suspend function used to insert questions and their state into the database.
 * setCurrentQuestion: A function used to set the current question being displayed to the user.
 * addAnswer is a function used to add an answer to the current question and update the currentAnswersMap.
 * updateAnswer is a function used to update the survey with the current answers in the database.
 * @property _uiState : MutableStateFlow variable that holds the state of questions and answers.
 * @property uiState : StateFlow variable that holds the state of questions and answers.
 * @param eventId : the id of the event the survey belongs to
 * @param boolean : a boolean to indicate whether the current question is a new one or not
 * @param context : the context of the application
 * @param answer : the answer string to be added
 */

open class QuestionViewModel : ViewModel() {
    val _uiState = MutableStateFlow(QuestionsUiState())
    val uiState: StateFlow<QuestionsUiState> = _uiState.asStateFlow()
    var progress = MutableStateFlow(0f)
    var page = MutableStateFlow(0)



    suspend fun insertQuestions(
        questionsUiState: QuestionsUiState,
        eventId: String
    ) {
        if (!uiState.value.questionsStarted) {
            _uiState.value = QuestionsUiState(
                questions = Database.getSurveyAsList(eventId = eventId),
            )
        }
        _uiState.value.questionsStarted = true
    }


    fun setCurrentQuestion(question: MyQuestion) {
        _uiState.value.currentQuestion = question
    }


    fun addAnswer(answer:String){
        _uiState.value.currentAnswersMap[_uiState.value.currentQuestion.mainQuestion] = answer
        _uiState.value.currentAnswers.add(answer)

    }



    fun updateAnswer(
        eventId: String, boolean: Boolean, context: Context
    ) {
        viewModelScope.launch {
            Database.updateSurvey(eventId = eventId, options = uiState.value.currentAnswers, newQuestion = uiState.value.currentQuestion,
                boolean = boolean, context = context
            )
        }
    }




}


