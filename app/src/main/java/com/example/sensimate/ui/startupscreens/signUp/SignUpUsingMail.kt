package com.example.sensimate.ui.InitialStartPage

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground
import com.example.sensimate.ui.startupscreens.signUp.myButton
import com.example.sensimate.ui.theme.PurpleButtonColor
import java.util.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.toSize
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.signUp.textFieldWithImage
import com.example.sensimate.ui.theme.Purple200

@Composable
fun SignUpUsingMail(navController: NavController) {

    InitialStartBackground()

    val showLoading = remember {
        mutableStateOf(false)
    }
    val successLoggedIn = remember {
        mutableStateOf(false)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        item {
            Spacer(modifier = Modifier.size(100.dp))
            SignMenus(
                navController = navController,
                screen = Screen.SignUpWithMail
            )

        }

        item {

            Spacer(modifier = Modifier.size(80.dp))

            //email button
            var email by remember { mutableStateOf("") }
            MyTextField(
                text = email,
                textSize = 15,
                onValueChange = { email = it },
                placeHolder = "Enter E-mail",
                width = 300,
                height = 51,
                KeyboardType.Email,
                visualTransformation = VisualTransformation.None,
                Color.DarkGray,
                Color.White,
                Color.Gray
            )

            Spacer(modifier = Modifier.size(45.dp))
            var password by remember { mutableStateOf("") }
            MyTextField(
                text = password,
                textSize = 15,
                onValueChange = { password = it },
                placeHolder = "Enter password",
                width = 300,
                height = 51,
                KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                Color.DarkGray,
                Color.White,
                Color.Gray
            )

            Spacer(modifier = Modifier.size(20.dp))
            var retyped by remember { mutableStateOf("") }
            MyTextField(
                text = retyped,
                textSize = 15,
                onValueChange = { retyped = it },
                placeHolder = "Confirm password",
                width = 300,
                height = 51,
                KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                Color.DarkGray,
                Color.White,
                Color.Gray
            )

            Spacer(modifier = Modifier.size(30.dp))

            var postalCode by remember { mutableStateOf("") }
            textFieldWithImage(
                painterResource(id = R.drawable.locationicon),
                text = postalCode,
                onValueChange = {
                    if (it.length <= 4) {
                        postalCode = it
                    }
                },
                "Postal code"
            )

            Spacer(modifier = Modifier.size(20.dp))
            val selectedGender = remember { mutableStateOf("") }

            DropDownMenu(selectedGender)


            val myYear = remember { mutableStateOf("") }
            val myMonth = remember { mutableStateOf("") }
            val myDay = remember { mutableStateOf("") }
            ChooseBirthDate(
                LocalContext.current,
                myYear = myYear,
                myMonth = myMonth,
                myDay = myDay
            )

            val showMessage = mutableStateOf(false)
            showMessage(
                message = "Passwords do not match",
                showMessage
            )

            val context = LocalContext.current
            Spacer(modifier = Modifier.size(60.dp))
            myButton(color = Color.White,
                title = "Sign up",
                PurpleButtonColor,
                onClick = {
                    if (password != retyped) {
                        showMessage.value = true
                    } else {

                        if (postalCode == "") {
                            Toast.makeText(
                                context,
                                "Remember to write your postal code",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (selectedGender.value == "") {
                            Toast.makeText(
                                context, "Remember to choose your gender",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (myYear.value == "") {
                            Toast.makeText(
                                context, "Remember to choose your date of birth",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Database.signUserUp(
                                email = email,
                                password = password,
                                context = context,
                                showLoading = showLoading,
                                postalCode = postalCode,
                                yearBorn = myYear.value,
                                monthBorn = myMonth.value,
                                dayBorn = myDay.value,
                                gender = selectedGender.value,
                                successLoggedIn = successLoggedIn
                            )
                        }
                    }
                }
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        showLoading(showLoading)
    }

    if (successLoggedIn.value) {
        navController.navigate(Screen.EventScreen.route)
        successLoggedIn.value = false
    }
}

@Composable
fun MyTextField(
    text: String,
    textSize: Int,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    width: Int,
    height: Int,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    myTextColor: Color,
    backgroundColor: Color,
    placeHolderColor: Color
) {
    Surface(
        modifier = Modifier.size(width.dp, height.dp),
        color = Color.White,
        shape = RoundedCornerShape(35),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = text,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(color = myTextColor),
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeHolder,
                        fontSize = textSize.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = manropeFamily,
                        textAlign = TextAlign.Left,
                        color = placeHolderColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = (height + 50).dp)
                    )
                },
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    backgroundColor = backgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewUsingMail() {
    SignUpUsingMail(
        rememberNavController()
    )
}

@Composable
fun ChooseBirthDate(
    context: Context,
    myYear: MutableState<String>,
    myMonth: MutableState<String>,
    myDay: MutableState<String>,
) {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, -18)

    // Create state variables to store the selected year, month, and day
    val selectedYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val selectedMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val selectedDay = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    val hasChosen = remember {
        mutableStateOf(false)
    }



    Log.d("myyear.value", myYear.value)
    Log.d("mymonth.value", myMonth.value)
    Log.d("myday.value", myDay.value)


    var text by remember { mutableStateOf(("")) }

    val datePickerLog =
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayofMonth: Int ->
                text = "$dayofMonth/${month + 1}/$year"
                // Update the selected year, month, and day
                selectedYear.value = year
                selectedMonth.value = month
                selectedDay.value = dayofMonth
                hasChosen.value = true
            }, selectedYear.value, selectedMonth.value, selectedDay.value
        )

    if (hasChosen.value) {
        myYear.value = selectedYear.value.toString()
        myMonth.value = (selectedMonth.value + 1).toString()
        myDay.value = selectedDay.value.toString()
    }

    datePickerLog.datePicker.maxDate = calendar.timeInMillis

    OutlinedTextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledIndicatorColor = Color.White,
            disabledLabelColor = Color.White
        ),
        enabled = false,
        value = text,
        label = { Text(text = "Enter Your date of Birth") },
        onValueChange = {},
        modifier = Modifier.clickable { datePickerLog.show() },
    )
}


@Composable
fun DropDownMenu(selectedGender: MutableState<String>) {

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Man", "Woman", "Other")


    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedGender.value,
            onValueChange = { selectedGender.value = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedBorderColor = Color.White,
                disabledPlaceholderColor = Color.White,
                disabledTextColor = Color.White,
                disabledBorderColor = Color.White
            ),
            enabled = false,
            modifier = Modifier
                .width(150.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Gender") },
            trailingIcon = {
                Icon(
                    icon, "",
                    Modifier.clickable { expanded = !expanded }, tint = Color.White
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedGender.value = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun showMessage(
    message: String,
    showMessage: MutableState<Boolean>,
) {

    if (showMessage.value) {
        AlertDialog(
            onDismissRequest = { showMessage.value = false },
            confirmButton = {
                TextButton(onClick = { showMessage.value = false })
                { Text(text = "OK") }
            },
            title = { Text(text = message) },
        )
    }
}

@Composable
fun showLoading(showloading: MutableState<Boolean>) {
    if (showloading.value) {
        CircularProgressIndicator(color = Purple200, modifier = Modifier.size(50.dp))
    }
}