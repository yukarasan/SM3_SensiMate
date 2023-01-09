package com.example.sensimate.data.questionandsurvey

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionsUiState())
    val uiState: StateFlow<QuestionsUiState> = _uiState.asStateFlow()

    suspend fun insertQuestions(
        questionsUiState: QuestionsUiState,
        eventId: String
    ) {
        if (!uiState.value.questionsStarted) {
            _uiState.value = QuestionsUiState(
                questions = Database.getSurveyAsList(eventId = eventId),
            )
            Log.d("indeni viewmodel", uiState.value.questions.size.toString())
        }
        _uiState.value.questionsStarted = true
    }

    fun setCurrentQuestion(question: MyQuestion){
        _uiState.value.currentQuestion = question
    }
}
