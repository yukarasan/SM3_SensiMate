package com.example.sensimate.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.EventDataViewModel
import com.example.sensimate.data.EventViewModel
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple


@Preview(showBackground = true)
@Composable
fun EventScreenEmployeePreview() {
    //EventScreenEmployee(rememberNavController(), )
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EventScreenEmployee(
    navController: NavController,
    dataViewModel: EventDataViewModel = viewModel(),
    eventViewModel: EventViewModel
) {

    val state = dataViewModel.state.value

    val state1 = eventViewModel.uiState
    val chosenEvent = eventViewModel.getEventById(state1.value.chosenSurveyId)

    Log.d("CHOSENNNNNN", chosenEvent.eventId)

    val showDialog = remember {
        mutableStateOf(false)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MyDialog(navController = navController, showDialog)
        }

        Column() {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 20.dp),
            ) {
                //val state = dataViewModel.state.value

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AddEventImage(navController = navController)
                        ProfileLogo(
                            Modifier
                                .clickable { showDialog.value = true }
                                .size(64.dp)
                                .padding(end = 13.dp, top = 15.dp))
                    }

                }
            }


            LazyColumn(
                contentPadding = PaddingValues(bottom = 20.dp),
            ) {
                state.events?.let {
                    items(it.toList()) { event ->

                        eventViewModel.insertEvent(event)

                        EventCard(
                            title = event.title,
                            hour = event.hour,
                            minute = event.minute,
                            address = event.location,
                            onClick = {
                                navController.navigate(
                                    Screen.EditEvent.route
                                )
                                eventViewModel.setChosenEventId(event.eventId)
                                Log.d("CLICKED",event.eventId )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun AddEventImage(navController: NavController) {
    val image = painterResource(id = R.drawable.add_event)
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .padding(start = 20.dp, top = 10.dp)
            .size(40.dp)
            .clickable {
                navController.navigate(Screen.CreateEventScreen.route)
            },
        alignment = Alignment.CenterEnd,
    )
}

@Preview(showBackground = true)
@Composable
private fun MyDialog(
    navController: NavController = rememberNavController(),
    showDialog: MutableState<Boolean> = mutableStateOf(true)
) {


    val context = LocalContext.current

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Do you want to log out of your profile?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false

                    Database.signOut(context = context)

                    navController.popBackStack()
                    navController.popBackStack()

                    navController.navigate(Screen.Login.route)
                })
                { Text(text = "Yes") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false })
                { Text(text = "No") }
            },
        )
    }
}

