import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.ui.createEvent.nonQuestion
import com.example.sensimate.ui.navigation.Screen
import com.google.api.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
/**
@author Ahmad Sandhu
QuestionPageViewModel class that handles the data for choose a question type
 */
data class QuestionUistate(
    val selectedQuestion: MutableState<String> =  mutableStateOf("")
)

class QuestionPageViewModel : ViewModel() {
    val _uistate = MutableStateFlow(QuestionUistate())
    val uistate: StateFlow<QuestionUistate> = _uistate.asStateFlow()


    fun checkQuestion(navController: NavController, context: android.content.Context) {
        when (uistate.value.selectedQuestion.value) {
            "Multiple-Choice Question" -> {
                navController.navigate(Screen.CreateMultpleChoiceQuestionScreen.route)
                nonQuestion +=1
                _uistate.value = _uistate.value.copy(selectedQuestion = mutableStateOf(""))
            }
            "Text Answer Question" -> {
                navController.navigate(Screen.CreateTextAnswerQuestionScreen.route)
                nonQuestion +=1
                _uistate.value = _uistate.value.copy(selectedQuestion = mutableStateOf(""))
            }
            else -> {
                Toast
                    .makeText(
                        context,
                        "Please Choose a Question Type",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }

    }

    fun checkNonQuestion(navController: NavController, context: android.content.Context){
        if(nonQuestion == 0){
            Toast
                .makeText(
                    context,
                    "No Question Created",
                    Toast.LENGTH_SHORT
                )
                .show()
        }else{
            navController.navigate(Screen.EventScreenEmployee.route){
                popUpTo(Screen.EventScreenEmployee.route){
                    inclusive = true
                }
                nonQuestion = 0
            }

        }
    }
}