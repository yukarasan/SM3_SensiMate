package com.example.sensimate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.EventDataViewModel
import com.example.sensimate.ui.navigation.Screen

@Composable
fun EventScreenEmployee(navController: NavController, dataViewModel: EventDataViewModel = viewModel()) {
    Column(
        modifier = Modifier.background(
            Brush.verticalGradient(
                0.0f to Color(83, 58, 134, 255),
                0.7f to Color(22, 26, 30)
            )
        )
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 20.dp),
        ) {
            val state = dataViewModel.state.value



            item {
                AddEventImage(navController = navController)
                TextToCreateEvent()
            }


            item { QuickEntry() }


            state.events?.let {
                items(it.toList()) { event ->
                    EventCard(
                        title = event.title,
                        timeOfEvent = event.timeOfEvent,
                        address = event.location,
                        onClick = {
                            navController.navigate(
                                Screen.ExtendedEventScreen.passArguments(
                                    time = event.timeOfEvent,
                                    title = event.title,
                                    description = event.description,
                                    allergens = event.allergens,
                                    location = event.location
                                )
                            )
                        }
                    )
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
            .padding(start = 20.dp)
            .size(40.dp)
            .clickable {
                navController.navigate(Screen.CreateEventScreen.route)
            },
        alignment = Alignment.CenterEnd,
    )
}
@Composable
fun TextToCreateEvent(){
    Text(text = "Crate Event",
        color = Color(0xFFB874A6), fontSize = 15.sp,
        maxLines = 1,
        modifier = Modifier
            .padding(start = 5.dp)

    )
}

