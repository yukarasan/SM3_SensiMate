package com.example.sensimate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.sensimate.App
//import com.example.sensimate.ExcelDownloader
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import java.io.File

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






