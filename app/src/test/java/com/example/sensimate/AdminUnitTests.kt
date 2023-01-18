package com.example.sensimate

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sensimate.ui.AdminScreens.AdminViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AdminUnitTests {

    @Before
    fun setup(){

    }

    @Test
    fun CreateEmployee(){
        runBlocking {
            val adminViewModel = AdminViewModel()
            adminViewModel.createdEmployee("something@some.some")
            val uiState = adminViewModel.uiState.value
            // check that uiState is not null before accessing its properties
            assertNotNull(uiState)
            assertEquals("something@some.some", uiState.mails.get(0))
        }
    }
}