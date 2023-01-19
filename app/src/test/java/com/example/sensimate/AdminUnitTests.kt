package com.example.sensimate


import com.example.sensimate.ui.AdminScreens.AdminViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**

AdminUnitTests is a class that contains test methods for the AdminViewModel class.

The setUp() method, annotated with @Before, sets the main dispatcher to Unconfined using the ExperimentalCoroutinesApi opt-in.

The CreateEmployee() method, annotated with @Test, creates an instance of the AdminViewModel class and
calls the createdEmployee method with a parameter of "something@some.some".
It then asserts that the uiState property is not null and that its mails property contains the expected value.
The cleanUp() method, annotated with @After and also using the ExperimentalCoroutinesApi opt-in,
sets the main dispatcher back to Default.

@author Hussein El-Zein
 */

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


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }


}