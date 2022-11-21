package com.example.sensimate.ui.survey


import android.renderscript.ScriptGroup
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.VisualTransformation
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.startupscreens.signUp.textFieldWithImage
import com.example.sensimate.ui.theme.*

@Preview(showBackground = true)
@Composable
private fun Survey3() {
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
            .padding(start = 20.dp, end = 20.dp, bottom = 0.dp, top = 10.dp)
    ) {
        OrangeBackButton({})
        ProgressPreview()
        Question(title = "Question 3/6")
        SurveyTitle(title = "What do you think about this image?")
        SurveyImage()
        Information3()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
        ) {
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
fun Information3() {
    val checkedState = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 0.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SurveyImage2()
                SurveyImage3()
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Information1(title = "1")
                Information1(title = "2")
                Information1(title = "3")
                Information1(title = "4")
                Information1(title = "5")

            }
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckView()
                RoundedCheckView()
                RoundedCheckView()
                RoundedCheckView()
                RoundedCheckView()

            }
        }
    }
}


@Composable
private fun SurveyImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.rectangle77) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .padding(top = 0.dp, end = 10.dp)
            .size(400.dp)
            .fillMaxWidth()

    )
}

@Composable
private fun SurveyImage2(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.thumbsdown) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .padding(start = 5.dp, top = 10.dp)
    )
}

@Composable
private fun SurveyImage3(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.heart) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .padding(top = 10.dp, end = 10.dp)
    )
}



@Composable
private fun Information1(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 5.dp, start = 20.dp, end = 20.dp)
    )
}
