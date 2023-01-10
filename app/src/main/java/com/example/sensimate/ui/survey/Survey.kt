package com.example.sensimate.ui.survey

import android.annotation.SuppressLint
import android.util.Log
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.StampedPathEffectStyle.Companion.Rotate
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sensimate.data.Database
import com.example.sensimate.data.EventViewModel
import com.example.sensimate.data.Profile
import com.example.sensimate.data.Survey
import com.example.sensimate.data.questionandsurvey.MyQuestion
import com.example.sensimate.data.questionandsurvey.QuestionViewModel
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun Survey(
    title: String,
    navController: NavController
) {
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
    LazyColumn() {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
            ) {
                OrangeBackButton({ navController.navigate(Screen.EventScreen.route) })
                ProgressPreview()
                Question(title)
                SurveyTitle(title)
                Information(
                    titles = listOf("Age", "Gender", "Postal code"),
                    placeholders = listOf("", "", "")
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 150.dp)
                ) {
                    //TODO: Remember to Implement Scaffold so the buttons does not move, but does not move
                   // PreviousButton(onClick = { navController.navigate(Screen.Survey.route) })
                   //NextButton(onClick = { navController.navigate(Screen.Survey2.route) })
                }
            }
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
        progress = 0.25f //TODO:  Needs state hoisting in future.

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
fun Information(titles: List<String>, placeholders: List<String>) {
    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            titles.forEachIndexed { index, title ->
                val placeholder = placeholders[index]
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 25.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Option(title = title, modifier = Modifier.padding(vertical = 10.dp))
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(end = 30.dp)
                    ) {
                        var value by remember { mutableStateOf("") }
                        MyTextField(
                            text = value,
                            textSize = 10,
                            onValueChange = { value = it },
                            placeHolder = placeholder,
                            width = 100,
                            height = 20,
                            keyboardType = KeyboardType.Number,
                            visualTransformation = VisualTransformation.None,
                            myTextColor = Color.White,
                            backgroundColor = GreyColor,
                            placeHolderColor = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun Option(title: String, modifier: Modifier = Modifier) {
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
fun PreviousButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(60),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(239, 112, 103)),
        modifier = Modifier
            .height(38.dp)
            .width(130.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
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
fun NextButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(PurpleButtonColor),
        modifier = Modifier
            .height(38.dp)
            .width(130.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                "Next",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 0.dp)
            )



            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_front2_24),
                contentDescription = "Next",
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 10.dp)
                    .rotate(180f)
            )

        }
    }

}





