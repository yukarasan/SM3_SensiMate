package com.example.sensimate.ui.home

import android.renderscript.ScriptGroup
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.example.sensimate.ui.InitialStartPage.textFieldWithImage
import com.example.sensimate.ui.theme.*

@Preview(showBackground = true)
@Composable
private fun Survery() {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0.0f to DarkPurple,
                    0.5f to BottonGradient
                )
            )
            .fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        OrangeBackButton()
        ProgressPreview()
        Question(title = "Question 1/6")
        SurveyTitle(title = "Let's first hear about yourself")
        Information()


    }


}


@Composable
private fun ProgressPreview() {
    LinearProgressIndicator(
        modifier = Modifier
            .padding(top = 20.dp, start = 0.dp)
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(15.dp)),
        backgroundColor = darkpurple,
        color = lightpurple, //progress color
        progress = 0.15f //TODO:  Needs state hoisting in future.

    )
}


@Composable
fun Question(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 5.dp, start = 0.dp)
    )
}


@Composable
fun SurveyTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 30.dp, start = 0.dp)
    )
}

@Composable
fun Information() {
    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InformationAge(title = "Age")
                var textFieldState by remember { mutableStateOf("") }
                InputField(
                    title = textFieldState,
                    onValueChange = { textFieldState = it },
                    "21"
                )

            }
            Row {
                InformationGender(title = "Gender")
                var textFieldState by remember { mutableStateOf("") }
                InputField(
                    title = textFieldState,
                    onValueChange = { textFieldState = it },
                    "Male"
                )
            }
            Row {
                InformationPostalCode(title = "Postal code")
                var textFieldState by remember { mutableStateOf("") }
                InputField(
                    title = textFieldState,
                    onValueChange = { textFieldState = it },
                    "3600"
                )
            }

        }
    }
}

@Composable
private fun InformationAge(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 20.dp, start = 20.dp)
    )
}

@Composable
private fun InformationGender(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 15.dp, start = 20.dp)
    )
}

@Composable
private fun InformationPostalCode(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 10.dp, start = 20.dp)
    )
}

@Composable
fun InputField(title: String, onValueChange: (String) -> Unit, placeHolder: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 220.dp, end = 20.dp, top = 0.dp, bottom = 0.dp)
            .height(50.dp)
    ) {

        TextField(
            value = title,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeHolder) },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .border(
                    width = 3.dp,
                    brush = Brush.horizontalGradient(
                        listOf(GreyColor, GreyColor)
                    ),
                    shape = RoundedCornerShape(35.dp)
                )
                .height(30.dp)
                .width(110.dp)
                .background(Color(74, 75, 90), shape = RoundedCornerShape(35.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1 //TODO: maxLines not working. Fix this.
        )
    }


}


@Composable
fun PreviousButton() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(60),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(239, 112, 103)),
        modifier = Modifier
            .height(38.dp)
            .width(130.dp)
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "Previous",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 20.dp, top = 0.dp)

            )
            Text(
                "Previous",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }

    }

}

@Composable
fun NextButton() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(PurpleButtonColor),
        modifier = Modifier
            .height(38.dp)
            .width(130.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.vector),
            contentDescription = "Next",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 20.dp)
        )
        Text(
            "Previous",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 20.dp)
        )
    }

}


