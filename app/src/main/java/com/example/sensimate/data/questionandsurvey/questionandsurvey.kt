package com.example.sensimate.data.questionandsurvey

data class QuestionsUiState(
    var questions: MutableList<Question> = emptyList<Question>().toMutableList()
)

class Question(
    var mainQuestion: String = "",
    var options: MutableList<String> = emptyList<String>().toMutableList(),
    var oneChoice: Boolean = false
)