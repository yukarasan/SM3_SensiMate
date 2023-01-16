package com.example.sensimate.ui.home

import android.annotation.SuppressLint
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.*
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.navigation.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.sensimate.ui.Event.viewModels.EventDataViewModel
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EventScreen(
    navController: NavController,
    dataViewModel: EventDataViewModel = viewModel(),
    eventViewModel: EventViewModel = viewModel()
) {
    val state = dataViewModel.state.value
    val isLoadingViewModel = viewModel<EventDataViewModel>()
    val isLoading by isLoadingViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val checked = remember { mutableStateOf<Boolean>(false) }

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
        if (state.events?.size != 0) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { dataViewModel.getListOfEvents() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = BottomGradient,
                        contentColor = Color.White
                    )
                }
            ) {
                Column() {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 20.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (checked.value) {
                                    Dialog(onDismissRequest = { /*TODO*/ }) {
                                        EventQuickEntry1(
                                            navController = navController,
                                            dataViewModel = dataViewModel,
                                            eventViewModel = eventViewModel,
                                            checked = checked
                                        )
                                    }

                                    /*
                                        Dialog(onDismissRequest = { /*TODO*/ }) {
                                            EventQuickEntry(navController = navController) { input ->
                                                val event = state.events?.find { it.eventId == input }
                                                if (event != null) {
                                                    navController.navigate(Screen.ExtendedEventScreen.route)
                                                    eventViewModel.setChosenEventId(event.eventId)
                                                } else {
                                                    incorrectEventCodeAlert = true
                                                }
                                            }
                                        }
                                     */
                                }
                                QuickEntryImage(
                                    modifier = Modifier
                                        .size(65.dp)
                                        .padding(top = 12.dp, start = 10.dp)
                                        .clickable(
                                            enabled = true,
                                            onClickLabel = "quick entry",
                                            onClick = {
                                                checked.value = true
                                            }
                                        )
                                )
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
                                eventViewModel.insertEvent(event)

                                EventCard(
                                    title = event.title,
                                    hour = event.hour,
                                    minute = event.minute,
                                    address = event.location,
                                    onClick = {
                                        navController.navigate(
                                            Screen.ExtendedEventScreen.route
                                        )
                                        eventViewModel.setChosenEventId(event.eventId)
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
        } else {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { dataViewModel.getListOfEvents() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = BottomGradient,
                        contentColor = Color.White
                    )
                }
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 20.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (checked.value) {
                                AlertDialog(onDismissRequest = { checked.value = false },
                                    text = {
                                        Text(
                                            "No events to join. Please wait until an " +
                                                    "event is displayed"
                                        )
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                checked.value = false
                                            }
                                        ) {
                                            Text(text = stringResource(id = R.string.ok))
                                        }
                                    }
                                )
                            }
                            QuickEntryImage(
                                modifier = Modifier
                                    .size(65.dp)
                                    .padding(top = 12.dp, start = 10.dp)
                                    .clickable(
                                        enabled = true,
                                        onClickLabel = "quick entry",
                                        onClick = {
                                            checked.value = true
                                        }
                                    )
                            )
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
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No events are currently available",
                        fontFamily = manropeFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
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

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EventQuickEntry1(
    navController: NavController,
    dataViewModel: EventDataViewModel,
    eventViewModel: EventViewModel,
    checked: MutableState<Boolean> = mutableStateOf(false)
) {
    var incorrectEventCodeAlert by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AlertQuickEntryImage(
                    modifier = Modifier
                        .size(50.dp)
                )
                Text(
                    text = "Quick Entry",
                    fontSize = 22.sp,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp),
                    textAlign = TextAlign.Left,
                    color = Color.White,
                )
            }

            TextField(
                value = eventViewModel.uiState.value.event.chosenSurveyCode.value,
                onValueChange = {
                    if (it.length <= 4) {
                        eventViewModel.updateSurveyCodeString(it)
                    }
                },
                label = { Text("Enter Survey Code", color = Color.White) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 2.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = DarkPurple,
                    unfocusedIndicatorColor = DarkPurple
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyButton(
                    color = Color.White,
                    title = "Submit",
                    buttonColor = BottomGradient,
                    onClick = {
                        // retrieve event from FireStore database using the survey code
                        dataViewModel.getEventBySurveyCode(
                            eventViewModel.uiState.value.event.chosenSurveyCode.value
                        ) { event ->
                            if (event != null) {
                                navController.navigate(Screen.ExtendedEventScreen.route)
                                eventViewModel.setChosenEventId(event.eventId)
                            } else {
                                incorrectEventCodeAlert = true
                            }
                        }
                    },
                    modifier = Modifier.padding(start = 30.dp, top = 10.dp, bottom = 10.dp)
                )
                MyButton(
                    color = Color.White,
                    title = "Cancel",
                    buttonColor = Color(184, 58, 58, 255),
                    onClick = {
                        checked.value = false
                    },
                    modifier = Modifier.padding(end = 30.dp, top = 10.dp, bottom = 10.dp)
                )
            }
        }
    }

    if (incorrectEventCodeAlert) {
        AlertDialog(
            onDismissRequest = { incorrectEventCodeAlert = false },
            title = { Text("Incorrect Survey Code") },
            text = { Text("The survey code you entered does not match any event.") },
            buttons = {
                Button(
                    onClick = {
                        incorrectEventCodeAlert = false
                    },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }
}

/*
@Composable
private fun EventQuickEntry(navController: NavController, param: (Any) -> Unit) {
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
                QuickEntryTitle("Quick Entry")
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

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    myButton(
                        onClick = { //måsske skal det være her
                            navController.navigate(Screen.Survey.route)
                        },
                        color = Color.Black, title = "Enter", buttonColor = Color(199, 242, 219)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    myButton(
                        onClick = { navController.navigate(Screen.EventScreen.route) },
                        color = Color.Black, title = "Back", buttonColor = Color.White
                    )
                }
            }
        }
    }
}
 */

@Composable
private fun QuickEntryImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Box(modifier = Modifier.padding(top = 5.dp, start = 10.dp)) {
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
private fun AlertQuickEntryImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
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
                .fillMaxWidth()
                .height(50.dp)
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
fun MyButton(
    color: Color,
    title: String,
    buttonColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = buttonColor
            ),
        modifier = modifier,
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

/*

@Composable
fun QrCode(data: String, size: Int) {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    val bitMatrix = MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, size, size, hints)
    val pixels = IntArray(size * size)
    for (y in 0 until size) {
        for (x in 0 until size) {
            pixels[y * size + x] = if (bitMatrix[x, y]) BLACK else WHITE
        }
    }
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).apply { setPixels(pixels, 0, size, 0, 0, size, size) }
    Image(bitmap = bitmap)
}




fun generateQRCode(content: String): Bitmap {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        val offset = y * width
        for (x in 0 until width) {
            pixels[offset + x] = if (bitMatrix.get(x, y)) BLACK else WHITE
        }
    }
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap


}

@Composable
fun showQrCode(data: String) {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    hints[EncodeHintType.MARGIN] = 2
    val qrCodeWriter = QRCodeWriter()
    val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250, hints)
    val bitmap = Bitmap.createBitmap(250, 250, Bitmap.Config.RGB_565)
    for (x in 0 until 250) {
        for (y in 0 until 250) {
            bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) BLACK else WHITE)
        }
    }
    Image(asset = imageResource(id = R.drawable.qr_code_image), contentDescription = "QR code")
}



fun generateQRCode2(content: String): Bitmap {
    val hints = HashMap<EncodeHintType, Any>()
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    hints[EncodeHintType.MARGIN] = 1

    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 256, 256, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val pixels = IntArray(width * height)

    for (y in 0 until height) {
        val offset = y * width
        for (x in 0 until width) {
            pixels[offset + x] = if (bitMatrix.get(x, y)) BLACK else WHITE
        }
    }
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap

}

 */


