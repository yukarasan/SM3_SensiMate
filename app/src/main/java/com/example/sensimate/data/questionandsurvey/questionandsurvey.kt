package com.example.sensimate.data.questionandsurvey

data class QuestionsUiState(
    var questions: List<MyQuestion> = emptyList<MyQuestion>().toMutableList(),
    var questionsStarted: Boolean = false,
    var currentQuestion: MyQuestion
)

class MyQuestion(
    var mainQuestion: String = "",
    var options: MutableList<String> = emptyList<String>().toMutableList(),
    var oneChoice: Boolean = false,
    var oneChoice2: Boolean = false
)