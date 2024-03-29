package com.example.sensimate.data.QuestionandSurvey

/**
 * QuestionsUiState class that holds the data for the questionnaires
 * @author Hussein El-Zein
 * @param questions - list of all questions
 * @param questionsStarted - whether the questionnaire has started or not
 * @param currentQuestion - the current question being displayed
 * @param currentAnswers - the current answers chosen by the user
 */
data class QuestionsUiState(
    var questions: List<MyQuestion> = emptyList<MyQuestion>().toMutableList(),
    var questionsStarted: Boolean = false,
    var currentQuestion: MyQuestion = MyQuestion(),
    var currentAnswers: MutableList<String> = mutableListOf(),
    var currentAnswersMap: MutableMap<String, String> = mutableMapOf()
)




/**
@author Hussein El-Zein
MyQuestion class that represents a question with its options
@param mainQuestion - the main question text
@param options - list of options for the question
@param oneChoice - whether the question allows only one choice
@param oneChoice2 - whether the question allows only one choice
 */
class MyQuestion(
    var mainQuestion: String = "",
    var options: MutableList<String> = emptyList<String>().toMutableList(),
    var oneChoice: Boolean = false,
    var oneChoice2: Boolean = false,
)
