package com.example.sensimate.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily

@Preview
@Composable
fun EditProfileScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(83, 58, 134, 255),
                    0.7f to Color(22, 26, 30)
                )
            ),
        contentPadding = PaddingValues(bottom = 80.dp, top = 20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                DoneButton()
            }
        }
        item { ImageButton() }
        item {
            CustomTextField(
                text = name,
                description = "Name",
                placeholder = "Enter your name here",
                onValueChange = { name = it }
            )
        }
        item {
            CustomTextField(
                text = email,
                description = "E-mail",
                placeholder = "Enter your e-mail here",
                onValueChange = { email = it }
            )
        }
        item {
            CustomPasswordField(
                text = password,
                description = "Password",
                placeholder = "Enter your password here",
                onValueChange = { password = it }
            )
        }
        item {
            CustomTextField(
                text = age,
                description = "Age",
                placeholder = "Enter your age here",
                onValueChange = { age = it }
            )
        }
        item {
            CustomTextField(
                text = gender,
                description = "Gender",
                placeholder = "Enter your gender here",
                onValueChange = { gender = it }
            )
        }
        item {
            CustomTextField(
                text = postalCode,
                description = "Postal code",
                placeholder = "Enter your postal code here",
                onValueChange = { postalCode = it }
            )
        }
    }
}


@Composable
private fun DoneButton() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(100),
        border = BorderStroke(3.dp, Color(199, 242, 219)),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(199, 242, 219)
        ),
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .padding(end = 20.dp)
    ) {
        Text(
            text = "Done",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
private fun ImageButton() {
    val image = painterResource(id = R.drawable.profilepic)
    val cameraIcon = painterResource(id = R.drawable.camera__2_)

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(180.dp)
            .padding(bottom = 5.dp)
            .clickable { /* TODO */ }
    ) {
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .clip(shape = CircleShape)
                .fillMaxSize(1f)
                .blur(4.dp)
                .alpha(0.5f),
        )
        Image(
            painter = cameraIcon,
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun CustomTextField(
    text: String,
    description: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, top = 10.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column() {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                text = description,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.White
            )
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                decorationBox = { innerTextField ->
                    Row() {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontFamily = manropeFamily,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            )
                        }
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(Color(154, 107, 254)),
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 2.dp),
                maxLines = 1,
                singleLine = true
            )
            Divider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}

// TODO: Give the function to show and hide the password.
@Composable
private fun CustomPasswordField(
    text: String,
    description: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, top = 10.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column() {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                text = description,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = manropeFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    decorationBox = { innerTextField ->
                        Row() {
                            if (text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = manropeFamily,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )
                            }
                            innerTextField()
                        }
                    },
                    cursorBrush = SolidColor(Color(154, 107, 254)),
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 2.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    maxLines = 1,
                    singleLine = true
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.eyeoff),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Divider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}