package com.example.sensimate.data.questionandsurvey

import androidx.lifecycle.ViewModel
import com.example.sensimate.data.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionsUiState())
    val uiState: StateFlow<QuestionsUiState> = _uiState.asStateFlow()

    fun insertQuestions(
        questionsUiState: QuestionsUiState,
        eventId: String
    ) {
        _uiState.value = QuestionsUiState(
            questions = Database.getSurveyAsList(eventId = eventId), //FÅ DEN HER TIL AT TAGE LISTE AF QUESTIONS FRA DATABASE
        )
    }
}