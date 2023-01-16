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
private val REQUEST_CODE_STORAGE_PERMISSION = 1000
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
    }
}

            /*

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_STORAGE_PERMISSION)
                    }
                    else {
                        download()
                    }
                }
                else{
                    download()
                }
            }



            private fun download() {

                val url = urlEt.text.toString()
                val request = DownloadManager.Request(Uri.parse(url))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                request.setTitle("Download")
                request.setDescription("The file is downloading...")

                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")

                val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                manager.enqueue(request)
            }

            override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
            ) {
                when(requestCode) {
                    REQUEST_CODE_STORAGE_PERMISSION -> {
                        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            download()
                        }
                        else {
                            Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            //questionViewModel.setExcelDownloader(this)


        }

             */
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

/*
private fun download(context:Context) {

    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED -> {
            // You can use the API that requires the permission.
            val request = DownloadManager.Request(
                Uri.parse(
                    File(
                        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                        "survey.xlsx"
                    ).toString()
                )
            )
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle("Download")
            request.setDescription("The file is downloading...")
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${System.currentTimeMillis()}"
            )

            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }

        /*  val request = DownloadManager.Request(Uri.parse(File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "survey.xlsx").toString()))
          request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
          request.setTitle("Download")
          request.setDescription("The file is downloading...")

          request.allowScanningByMediaScanner()
          request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
          request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")

          val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
          manager.enqueue(request)

         */


        else -> {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1000
            )
        }
    }

 */





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






