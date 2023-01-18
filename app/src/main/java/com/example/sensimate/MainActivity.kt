package com.example.sensimate

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sensimate.App
import com.example.sensimate.data.Database

//import com.example.sensimate.ExcelDownloader
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import kotlinx.coroutines.launch
import java.io.File

/**
 * MainActivity is the entry point of the app. And it sets the content to be the App composable.
 */
    class MainActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(context = LocalContext.current)
        }
    }
}




