package com.example.sensimate.ui.InitialStartPage

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.sensimate.navigation.Screen
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.startupscreens.signUp.InitialStartBackground
import com.example.sensimate.ui.startupscreens.signUp.myButton
import com.example.sensimate.ui.theme.PurpleButtonColor
import java.util.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize

@Composable
fun SignUpUsingMail(navController: NavController) {

    InitialStartBackground()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp)
        ) {
            OrangeBackButton({ navController.popBackStack() })
        }

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

        //
        var selectedText = remember{mutableStateOf("")}
        DropDownMenu(selectedText)

        Spacer(modifier = Modifier.size(10.dp))

        ChooseBirthDate(LocalContext.current)
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        myButton(color = Color.White,
            title = "Sign up",
            PurpleButtonColor,
            onClick = { navController.navigate(Screen.EventScreen.route) }
        )
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
                        fontWeight = FontWeight.Bold,
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
fun ChooseBirthDate(context: Context) {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, -18)

    // Create state variables to store the selected year, month, and day
    val selectedYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val selectedMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val selectedDay = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

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
            }, selectedYear.value, selectedMonth.value, selectedDay.value
        )

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
        modifier = Modifier.clickable {datePickerLog.show()},
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
                Icon(icon, "",
                    Modifier.clickable { expanded = !expanded }, tint = Color.White)
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