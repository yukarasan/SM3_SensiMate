package com.example.sensimate

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
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
private val REQUEST_CODE_STORAGE_PERMISSION = 1
/**
 * MainActivity is the entry point of the app. And it sets the content to be the App composable.
 */
    class MainActivity : ComponentActivity() /*ExcelDownloader*/ {
        private val questionViewModel = QuestionViewModel()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                App(context = LocalContext.current)

            }
            //questionViewModel.setExcelDownloader(this)


        }
/*
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val context: Context = MainActivity()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can write to external storage

                    //Database.test2(context = context, eventId = eventId, options = uiState.value.currentAnswers, newQuestion = uiState.value.currentQuestion)
                }
            } else {
                Toast.makeText(this, "Permission denied, please provide storage permission", Toast.LENGTH_LONG).show()
            }
        }

 */
    }









/*
    fun downloadExcel() {
        val file = File(getExternalFilesDir(null), "survey.xlsx")
        if (file.exists()) {
            val uri = FileProvider.getUriForFile(this, "com.example.file-provider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/vnd.ms-excel")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
            Log.d("VIRKER", "VIRKER")
        } else {
            Log.d("FEJL", "FEJL")
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
        }



    }

 */






