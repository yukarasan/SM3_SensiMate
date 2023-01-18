package com.example.sensimate.ui.Event

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

/**
 * EventScreen is a composable function that takes in a navigation controller, an
 * EventDataViewModel and an EventViewModel as arguments.
 * It displays a list of events in a swipeable layout and also has a quick entry feature
 * for creating new events.
 * When no events are available, it will display a screen that displays that to the user.
 * Additionally, it has a profile button that navigates to the profile screen when clicked.
 * @author Yusuf Kara
 */
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

    val checked = remember { mutableStateOf(false) }

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
                                    Dialog(onDismissRequest = { checked.value = false }) {
                                        EventQuickEntry(
                                            navController = navController,
                                            dataViewModel = dataViewModel,
                                            eventViewModel = eventViewModel,
                                            checked = checked
                                        )
                                    }
                                }
                                QuickEntryImage(
                                    modifier = Modifier
                                        .size(65.dp)
                                        .padding(top = 12.dp, start = 10.dp)
                                        .clickable(
                                            enabled = true,
                                            onClickLabel = stringResource(id = R.string.quickEntryLower),
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
                                            onClickLabel = stringResource(id = R.string.profile),
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
                                        Text(stringResource(id = R.string.noEventsAvailableToJoin))
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
                                        onClickLabel = stringResource(id = R.string.quickEntryLower),
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
                                        onClickLabel = stringResource(id = R.string.profile),
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
                        text = stringResource(id = R.string.noEventsAvailable),
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

/**
 * This composable displays the profile logo, which has an modifier as a parameter. This
 * modifier is then used to make the image clickable and thereby making the user navigate
 * to the profile screen.
 * @author Yusuf Kara
 */
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

/**
 * EventQuickEntry creates a card with a quick entry feature for creating new events by entering
 * a survey code.
 * It also allows to navigate to the ExtendedEventScreen when the survey code entered corresponds
 * to a specific event with that survey code.
 * If the code entered does not correspond to an event, an alert is displayed telling the user
 * that the survey code does not match any event.
 * @author Yusuf Kara
 */
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EventQuickEntry(
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
                    .padding(start = 20.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AlertQuickEntryImage(
                    modifier = Modifier
                        .size(50.dp)
                )
                Text(
                    text = stringResource(id = R.string.quickEntry),
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
                label = {
                    Text(
                        stringResource(id = R.string.enterSurveyCode),
                        color = Color.White
                    )
                },
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
                    title = stringResource(id = R.string.submit),
                    buttonColor = BottomGradient,
                    onClick = {
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
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                )
                MyButton(
                    color = Color.White,
                    title = stringResource(id = R.string.cancel),
                    buttonColor = Color(184, 58, 58, 255),
                    onClick = {
                        checked.value = false
                    },
                    modifier = Modifier.padding(end = 10.dp, top = 10.dp, bottom = 10.dp)
                )
            }
        }
    }

    if (incorrectEventCodeAlert) {
        AlertDialog(
            onDismissRequest = { incorrectEventCodeAlert = false },
            title = {
                Text(stringResource(id = R.string.incorrectSurveyCode))
            },
            text = {
                Text(stringResource(id = R.string.surveyCodeDoesNotMatch))
            },
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

/**
 * QuickEntryImage is a private composable that displays an image quick entry button for
 * creating new events.
 * @author Yusuf Kara
 */
@Composable
private fun QuickEntryImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Box(modifier = Modifier.padding(top = 5.dp, start = 10.dp)) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = modifier
                .size(90.dp)
        )
    }
}

/**
 * This image is meant to be used in the quick entry when the quick entry is displayed as an
 * alert.
 * @author Yusuf Kara
 */
@Composable
private fun AlertQuickEntryImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_add_circle_outlined)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
}

/**
 * MyButton is used in the quick entry. It takes different arguments, to allow for different
 * versions of the button, including text, color and so on.
 * This composable was originally made by Ansh, but later modified by Yusuf
 * @author Ansh & Yusuf.
 */
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

/**
 * The following out-commented code is functions that we could implement in the future, that would
 * be nice to have on the app. For now, they are not necessary, which is why we have left them out.
 */

/*
@Composable
private fun Label() {
    Text(
        text = "Enter event code",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}
 */

/*
@Composable
private fun Placeholder() {
    Text(
        text = "Enter event code here to open the survey",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.White
    )
}
 */

/*
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
 */

/*
@Composable
private fun EventInputField(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        // ---------------------------------------------------------------------------
        // Needs state hoisting
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
            maxLines = 1
        )
    }
}
 */

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


