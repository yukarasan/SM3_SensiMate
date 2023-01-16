package com.example.sensimate.data.questionandsurvey

import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensimate.data.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.darkbluegrey
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.MainActivity
//import com.example.sensimate.ExcelDownloader
import com.example.sensimate.ui.startupscreens.splashscreen.SplashScreen
import kotlinx.coroutines.delay

open class QuestionViewModel : ViewModel() {
    val _uiState = MutableStateFlow(QuestionsUiState())
    val uiState: StateFlow<QuestionsUiState> = _uiState.asStateFlow()


    suspend fun insertQuestions(
        questionsUiState: QuestionsUiState,
        eventId: String
    ) {
        if (!uiState.value.questionsStarted) {
            _uiState.value = QuestionsUiState(
                questions = Database.getSurveyAsList(eventId = eventId),
            )
        }
        _uiState.value.questionsStarted = true
    }


    fun setCurrentQuestion(question: MyQuestion) {
        _uiState.value.currentQuestion = question
    }
    fun setAnswer(answers: List<String>) {
        //_uiState.value.currentAnswers = listOf(answer)
        _uiState.value.currentAnswers = answers
    }

    fun updateAnswer(
        eventId: String, boolean: Boolean, context: Context
    ) {

        //Database.requestStoragePermission(context as Activity)
        viewModelScope.launch {
            Database.updateSurvey(eventId = eventId, options = uiState.value.currentAnswers, newQuestion = uiState.value.currentQuestion,
                boolean = boolean, context = context
            )
        }
    }




    fun excel(eventId: String) {
        viewModelScope.launch {
            //Database.updateSurvey2(eventId = eventId, options = uiState.value.currentAnswers, newQuestion = uiState.value.currentQuestion)
            //excelDownloader.downloadExcel()
        }
    }

}




/*
    fun getAnswer(question: MyQuestion): StateFlow<MyAnswer> {
        val _myanswer = MutableStateFlow(MyAnswer())
        return _myanswer.asStateFlow()
    }

 */




