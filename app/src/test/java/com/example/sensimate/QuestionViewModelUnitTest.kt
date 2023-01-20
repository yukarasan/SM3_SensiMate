package com.example.sensimate

import com.example.sensimate.data.QuestionandSurvey.MyQuestion
import com.example.sensimate.data.QuestionandSurvey.QuestionViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * This is a unit test that tests the QuestionViewModel() class i created earlier.
 * Its function that is tested is setCurrentQuestion. If it works, we can set the current question
 * from surveycreator
 * @author Hussein El-Zein
 * */
class QuestionViewModelUnitTest {


    @Test
    fun questionViewModelUnitTest(){

        val questionvm = QuestionViewModel()

        val question = MyQuestion()
        question.mainQuestion = "this is a main question"

        questionvm.setCurrentQuestion(question)


        assertEquals("this is a main question", questionvm.uiState.value.currentQuestion.mainQuestion)
    }

}