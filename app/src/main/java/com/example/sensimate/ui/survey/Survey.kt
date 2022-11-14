package com.example.sensimate.ui.survey

import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.VisualTransformation
import com.example.sensimate.ui.InitialStartPage.MyTextField
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
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        OrangeBackButton()
        ProgressPreview()
        Question(title = "Question 1/6")
        SurveyTitle(title = "Let's first hear about yourself")
        Information()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 250.dp)
        ) {
            //TODO: Remember to Implement Scaffold so the buttons does not move, but does not move
            PreviousButton()
            NextButton()
        }
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
        Column() {
            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InformationAge(title = "Age")
                
                Spacer(modifier = Modifier.width((120.dp)))

                var age by remember { mutableStateOf("") }
                MyTextField(
                    text = age,
                    textSize = 10,
                    onValueChange = {age = it} ,
                    placeHolder = "" ,
                    width = 100,
                    height = 20,
                    keyboardType = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None,
                    myTextColor = Color.White,
                    backgroundColor = GreyColor,
                    placeHolderColor = Color.White
                )

            }
            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InformationGender(title = "Gender")
                Spacer(modifier = Modifier.width((120.dp)))
                var gender by remember { mutableStateOf("") }
                MyTextField(
                    text = gender,
                    textSize = 12,
                    onValueChange = {gender = it} ,
                    placeHolder = "" ,
                    width = 100,
                    height = 20,
                    keyboardType = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None,
                    myTextColor = Color.White,
                    backgroundColor = GreyColor,
                    placeHolderColor = Color.Gray
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InformationPostalCode(title = "Postal code")
                Spacer(modifier = Modifier.width((120.dp)))
                var postalcode by remember { mutableStateOf("") }
                MyTextField(
                    text = postalcode ,
                    textSize = 12,
                    onValueChange = {postalcode = it} ,
                    placeHolder = "" ,
                    width = 100,
                    height = 20,
                    keyboardType = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None,
                    myTextColor = Color.White,
                    backgroundColor = GreyColor,
                    placeHolderColor = Color.Gray
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
                    .padding(start = 0.dp)

            )
            Text(
                "Previous",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 0.dp)
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
        Text(
            "Next",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 0.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.vector),
            contentDescription = "Next",
            modifier = Modifier
                .size(25.dp)
                .padding(start = 10.dp)
        )

    }

}





