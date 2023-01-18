package com.example.sensimate

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.sensimate.data.SaveBoolToLocalStorage
import com.example.sensimate.data.getBooleanFromLocalStorage
import junit.framework.TestCase
import org.junit.Test

class TestLocalStorage {


    class TestLocalStorage() {


        @Test
        @Composable
        fun TestSaveToBool() {

            val context = LocalContext.current

            SaveBoolToLocalStorage(
                key = "isLoggedIn",
                value = true,
                context
            )

            TestCase.assertEquals(
                true,
                getBooleanFromLocalStorage("isLoggedIn", context)
            )


        }

    }
}