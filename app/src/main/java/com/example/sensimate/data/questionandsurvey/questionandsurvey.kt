package com.example.sensimate.data.questionandsurvey

import com.example.sensimate.data.Database

data class QuestionsUiState(
    var questions: List<MyQuestion> = emptyList<MyQuestion>().toMutableList()
)

class MyQuestion(
    var mainQuestion: String = "",
    var options: MutableList<String> = emptyList<String>().toMutableList(),
    var oneChoice: Boolean = false
)