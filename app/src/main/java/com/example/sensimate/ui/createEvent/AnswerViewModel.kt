import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.ui.createEvent.nonQuestion
import com.example.sensimate.ui.navigation.Screen
import com.google.api.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author Ahmad Sandhu
 * AnswerViewModel class that handles the data for Multiple Choice Question
 */
data class AnswerUistate(
    var questionText: MutableState<String> = mutableStateOf(""),
    var answerText1: MutableState<String> = mutableStateOf(""),
    var answerText2: MutableState<String> = mutableStateOf(""),
    var answerText3: MutableState<String> = mutableStateOf(""),
    var answerText4: MutableState<String> = mutableStateOf(""),
    var answerText5: MutableState<String> = mutableStateOf(""),
    val checkedState: MutableState<Boolean> =  mutableStateOf(false)
)

class AnswerViewModel: ViewModel(){
    val  _uistate = MutableStateFlow(AnswerUistate())
    val uistate: StateFlow<AnswerUistate> = _uistate.asStateFlow()

    fun multipleAnswer(navController: NavController,context: android.content.Context){
        if (uistate.value.answerText5.value != ""){
            if (uistate.value.questionText.value == "") {
                Toast.makeText(
                    context,
                    "Question was not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                Database.question5(
                    question = uistate.value.questionText.value,
                    boolean = uistate.value.checkedState.value,
                    answer1 = uistate.value.answerText1.value,
                    answer2 = uistate.value.answerText2.value,
                    answer3 = uistate.value.answerText3.value,
                    answer4 = uistate.value.answerText4.value,
                    answer5 = uistate.value.answerText5.value
                )
                navController.navigate(Screen.QuestionPageScreen.route)
                _uistate.value = _uistate.value.copy(questionText = mutableStateOf(""),
                    answerText1 = mutableStateOf(""), answerText2 = mutableStateOf(""),
                    answerText3 = mutableStateOf(""), answerText4 = mutableStateOf(""), answerText5 = mutableStateOf(""), checkedState = mutableStateOf(false)
                )
            }
        }
        else if (uistate.value.answerText4.value != ""){
            if (uistate.value.questionText.value == "") {
                Toast.makeText(
                    context,
                    "Question was not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                Database.question4(
                    question = uistate.value.questionText.value,
                    boolean = uistate.value.checkedState.value,
                    answer1 = uistate.value.answerText1.value,
                    answer2 = uistate.value.answerText2.value,
                    answer3 = uistate.value.answerText3.value,
                    answer4 = uistate.value.answerText4.value

                )
                navController.navigate(Screen.QuestionPageScreen.route)
                _uistate.value = _uistate.value.copy(questionText = mutableStateOf(""),
                    answerText1 = mutableStateOf(""), answerText2 = mutableStateOf(""),
                    answerText3 = mutableStateOf(""), answerText4 = mutableStateOf(""), answerText5 = mutableStateOf(""), checkedState = mutableStateOf(false)
                )
            }
        }
        else if (uistate.value.answerText3.value != ""){
            if (uistate.value.questionText.value == "") {
                Toast.makeText(
                    context,
                    "Question was not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                Database.question3(
                    question = uistate.value.questionText.value,
                    boolean = uistate.value.checkedState.value,
                    answer1 = uistate.value.answerText1.value,
                    answer2 = uistate.value.answerText2.value,
                    answer3 = uistate.value.answerText3.value
                )
                navController.navigate(Screen.QuestionPageScreen.route)
                _uistate.value = _uistate.value.copy(questionText = mutableStateOf(""),
                    answerText1 = mutableStateOf(""), answerText2 = mutableStateOf(""),
                    answerText3 = mutableStateOf(""), answerText4 = mutableStateOf(""), answerText5 = mutableStateOf(""), checkedState = mutableStateOf(false)
                )
            }
        }
        else{
            if (uistate.value.questionText.value == "") {
                Toast.makeText(
                    context,
                    "Question was not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (uistate.value.answerText1.value == "") {
                Toast.makeText(
                    context,
                    "Answer 1 was not entered",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (uistate.value.answerText2.value == "") {
                Toast.makeText(
                    context,
                    "Answer 2 was not entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
                else{
                    Database.question(
                        question = uistate.value.questionText.value,
                        boolean = uistate.value.checkedState.value,
                        answer1 = uistate.value.answerText1.value,
                        answer2 = uistate.value.answerText2.value,

                        )
                navController.navigate(Screen.QuestionPageScreen.route)
                _uistate.value = _uistate.value.copy(questionText = mutableStateOf(""),
                    answerText1 = mutableStateOf(""), answerText2 = mutableStateOf(""),
                    answerText3 = mutableStateOf(""), answerText4 = mutableStateOf(""), answerText5 = mutableStateOf(""), checkedState = mutableStateOf(false)
                )
                }

        }


    }

    fun goBack(navController: NavController){
        nonQuestion-=1
        navController.popBackStack()
    }
















}