package com.example.sensimate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.*
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen

@Composable
fun EventScreen(navController: NavController, dataViewModel: EventDataViewModel = viewModel()) {
    val state = dataViewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(83, 58, 134, 255),
                    0.7f to Color(22, 26, 30)
                )
            )
    ) {
        Column() {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 20.dp),
            ) {
                item {
                    ProfileLogo(
                        modifier = Modifier
                            .size(95.dp)
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 20.dp)
                            .clickable(enabled = true,
                                onClickLabel = "profile",
                                onClick = {
                                    navController.navigate(Screen.ProfileScreen.route)
                                }
                            )
                    )
                }
                item { EventQuickEntry() }

                state.events?.let {
                    items(it.toList()) { event ->
                        EventCard(
                            title = event.title,
                            distance = event.distanceToEvent,
                            address = event.address,
                            onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                        )
                    }
                }

                /*
                val events = mutableListOf<Event>()
                val eventReference = db.collection("events")

                eventReference.get()
                    .addOnSuccessListener { collection ->
                        for (document in collection) {
                            val event = document.toObject(Event::class.java)
                            events.add(event)
                        }


                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "Error getting events: ", exception)
                    }
                 */
            }
        }
    }
}

@Composable
private fun ProfileLogo(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.person_circle)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier,
        alignment = Alignment.CenterEnd
    )
}

@Composable
private fun EventQuickEntry() {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 10.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column() {
            Row() {
                QuickEntryImage()
                QuickEntryTitle("Quick Entry") //TODO: Make text as recourse
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                EventInputField({})
            }
        }
    }
}

@Composable
private fun QuickEntryImage() {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Box(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 10.dp, bottom = 5.dp)) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .fillMaxSize()
        )
    }
}

@Composable
private fun QuickEntryTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 25.dp, start = 0.dp)
    )
}

@Composable
private fun EventInputField(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        // ---------------------------------------------------------------------------
        //TODO: Needs state hoisting
        var text by remember { mutableStateOf(TextFieldValue("")) }
        // ---------------------------------------------------------------------------
        TextField(
            value = text,
            onValueChange = { it -> text = it },
            label = { Label() },
            placeholder = { Placeholder() },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .border(
                    width = 3.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(74, 75, 90),
                            Color(74, 75, 90)
                        )
                    ),
                    shape = RoundedCornerShape(35.dp)
                )
                .width(400.dp)
                .height(40.dp)
                .background(
                    Color(74, 75, 90),
                    shape = RoundedCornerShape(35.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1 //TODO: maxLines not working. Fix this.
        )
    }
}

@Composable
private fun Label() {
    Text(
        text = "Enter event code", //TODO: Make text as recourse
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}

@Composable
private fun Placeholder() {
    Text(
        text = "Enter event code here to open the survey", //TODO: Make text as recourse
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}