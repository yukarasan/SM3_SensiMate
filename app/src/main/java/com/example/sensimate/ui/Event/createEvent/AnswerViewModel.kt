import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.ui.Event.createEvent.CreateEventUistate
import com.example.sensimate.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun multipleAnswer(navController: NavController){
        if (uistate.value.answerText5.value != ""){
            Database.question5(
                question = uistate.value.questionText.value,
                boolean = uistate.value.checkedState.value,
                answer1 = uistate.value.answerText1.value,
                answer2 = uistate.value.answerText2.value,
                answer3 = uistate.value.answerText3.value,
                answer4 = uistate.value.answerText4.value,
                answer5 = uistate.value.answerText5.value
            )
        }
        else if (uistate.value.answerText4.value != ""){
            Database.question4(
                question = uistate.value.questionText.value,
                boolean = uistate.value.checkedState.value,
                answer1 = uistate.value.answerText1.value,
                answer2 = uistate.value.answerText2.value,
                answer3 = uistate.value.answerText3.value,
                answer4 = uistate.value.answerText4.value

            )
        }
        else if (uistate.value.answerText3.value != ""){
            Database.question3(
                question = uistate.value.questionText.value,
                boolean = uistate.value.checkedState.value,
                answer1 = uistate.value.answerText1.value,
                answer2 = uistate.value.answerText2.value,
                answer3 = uistate.value.answerText3.value
            )
        }
        else{
            Database.question(
                question = uistate.value.questionText.value,
                boolean = uistate.value.checkedState.value,
                answer1 = uistate.value.answerText1.value,
                answer2 = uistate.value.answerText2.value,

                )

        }

        navController.navigate(Screen.QuestionPageScreen.route)
        _uistate.value = _uistate.value.copy(questionText = mutableStateOf(""),
            answerText1 = mutableStateOf(""), answerText2 = mutableStateOf(""),
        answerText3 = mutableStateOf(""), answerText4 = mutableStateOf(""), answerText5 = mutableStateOf(""), checkedState = mutableStateOf(false)
        )
    }
















}