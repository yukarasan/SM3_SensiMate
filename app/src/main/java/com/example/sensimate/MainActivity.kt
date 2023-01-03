package com.example.sensimate

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import android.view.WindowManager
import androidx.compose.foundation.layout.Column

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.darkbluegrey


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = DarkPurple.toArgb()
        }

        // WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ChangeSystemBarColor()
            App()
        }
    }

    @Composable
    private fun ChangeSystemBarColor() {
        MaterialTheme {
            Surface(color = DarkPurple) {
                Column {
                    Text(text = "Sensimate")
                }
            }
        }
    }
}





/*
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        if (windowInsetsController != null) {
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        windowInsetsController?.Colorhide(WindowInsetsCompat.Type.systemBars())
    }

 */
