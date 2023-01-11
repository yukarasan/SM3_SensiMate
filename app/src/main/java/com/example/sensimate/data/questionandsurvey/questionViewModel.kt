package com.example.sensimate.data.questionandsurvey

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.sensimate.data.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionViewModel : ViewModel() {
    val _uiState = MutableStateFlow(QuestionsUiState())
    val uiState: StateFlow<QuestionsUiState> = _uiState.asStateFlow()

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
    fun setAnswer(answers: List<String>) {
        //_uiState.value.currentAnswers = listOf(answer)
        _uiState.value.currentAnswers = answers
    }


}




/*
    fun getAnswer(question: MyQuestion): StateFlow<MyAnswer> {
        val _myanswer = MutableStateFlow(MyAnswer())
        return _myanswer.asStateFlow()
    }

 */




