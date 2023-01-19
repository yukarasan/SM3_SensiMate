package com.example.sensimate


import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sensimate.ui.AdminScreens.AdminViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class AdminUnitTests {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun CreateEmployee() {
        val adminViewModel = AdminViewModel()
        adminViewModel.createdEmployee("something@some.some")

        // check that uiState is not null before accessing its properties
        assertNotNull(adminViewModel.uiState.value.mails)
        assertEquals("something@some.some", adminViewModel.uiState.value.mails.get(0))
    }


/*
    @Test
    fun CreateThenDeleteEmployee() {
        //val context = LocalContext.current

        val adminViewModel = AdminViewModel()

        adminViewModel.createdEmployee("something@some.some")


        adminViewModel.clickOnDeleteEmployee("something@some.some", context)


        //check that uiState is not null before accessing its properties


        assertEquals(0, adminViewModel.uiState.value.mails.size)
    }

 */


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }


}