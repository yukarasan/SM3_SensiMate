package com.example.sensimate.ui.home

import android.graphics.Color.WHITE
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.*
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun EventScreen(navController: NavController, dataViewModel: EventDataViewModel = viewModel()) {
    val state = dataViewModel.state.value
    var checked by remember { mutableStateOf(false) }

    Log.d("jdjd", "EventScreen: " + checked)
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        if (checked) {
                            Dialog(onDismissRequest = { /*TODO*/ }) {
                                EventQuickEntry(navController = navController)
                            }
                        }
                        QuickEntryImage(
                            modifier = Modifier
                                .size(65.dp)
                                .padding(top = 12.dp, start = 10.dp)
                                .clickable(enabled = true,
                                    onClickLabel = "quick entry",
                                    onClick = {
                                        checked = true
                                    }
                                ))
                        ProfileLogo(
                            modifier = Modifier
                                .size(72.dp)
                                .padding(top = 20.dp, end = 20.dp)
                                .clickable(enabled = true,
                                    onClickLabel = "profile",
                                    onClick = {
                                        navController.navigate(Screen.ProfileScreen.route)
                                    }
                                )
                        )
                    }
                }

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
fun ProfileLogo(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.person_circle)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier,
        alignment = Alignment.TopEnd
    )
}

@Composable
private fun EventQuickEntry(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 10.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column(
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuickEntryImage2()
                QuickEntryTitle("Quick Entry") //TODO: Make text as recourse
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp, top = 20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EventInputField({})
                }
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                    .padding(top = 20.dp)
                )

                {
                    myButton(
                        onClick = { navController.navigate(Screen.Survey.route) },
                        color = Color.Black, title = "Enter", buttonColor = Color(199, 242, 219)
                    )
                }
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    myButton(onClick  ={ navController.navigate(Screen.EventScreen.route)},
                        color = Color.Black, title = "Back", buttonColor = Color.White
                    )
                    }
                }
            }
        }
    }


@Composable
private fun QuickEntryImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Box(modifier = Modifier.padding(top = 5.dp, start = 20.dp)) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = modifier
               // .fillMaxSize()
                .size(90.dp)
        )
    }
}


@Composable
private fun QuickEntryImage2(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Box(modifier = Modifier.padding(top = 5.dp, start = 15.dp)) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = modifier
                // .fillMaxSize()
                .size(50.dp)
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
            .padding(top = 5.dp, end = 30.dp)
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

@Composable
fun myButton(
    color: Color,
    title: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = buttonColor
            ),
        modifier = Modifier.size(110.dp, 35.dp),
        shape = CircleShape,
    ) {
        Text(
            title,
            color = color,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}