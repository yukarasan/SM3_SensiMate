import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.ui.navigation.Screen
import com.google.api.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TextAnswerUistate(
    var questionText: MutableState<String> = mutableStateOf("")
)
class TextAnswerViewModel :ViewModel(){
    val  _uistate = MutableStateFlow(TextAnswerUistate())
    val uistate: StateFlow<TextAnswerUistate> = _uistate.asStateFlow()



    fun textAnswer(navController: NavController, context: android.content.Context){
        if (uistate.value.questionText.value == "") {
            Toast.makeText(
                context,
                "Question was not entered",
                Toast.LENGTH_SHORT
            ).show()
        }else {
            Database.textAnswer(question = uistate.value.questionText.value)
            navController.navigate(Screen.QuestionPageScreen.route)
            _uistate.value = _uistate.value.copy(questionText = mutableStateOf(""))
        }

    }
}