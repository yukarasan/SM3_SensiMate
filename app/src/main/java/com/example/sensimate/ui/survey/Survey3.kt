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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.signUp.textFieldWithImage
import com.example.sensimate.ui.theme.*
//@Preview(showBackground = true)
@Composable
fun Survey3(navController: NavController) {
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
        OrangeBackButton({navController.navigate(Screen.EventScreen.route)})
        ProgressPreview()
        Question(title = "Question 3/4")
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
            PreviousButton(onClick = { navController.navigate(Screen.Survey2.route) } )
            NextButton(onClick = { navController.navigate(Screen.Survey4.route) } )
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
        progress = 0.75f //TODO:  Needs state hoisting in future.

    )
}


@Composable
fun Information3() {
    val checkedState = remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(0) }
    var listener: ((option: Int, value: Boolean) -> Unit)? = { i: Int, b: Boolean ->
        selectedOption = i
    }
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

                RoundedCheckView(listener, selectedOption, option = 0)
                RoundedCheckView(listener, selectedOption, option = 1)
                RoundedCheckView(listener,selectedOption, option = 2)
                RoundedCheckView(listener, selectedOption, option = 3)
                RoundedCheckView(listener,selectedOption, option = 4)





            }
        }
    }
}


@Composable
private fun SurveyImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.drinkingdude) // Possible for hoisting in future.
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .size(320.dp)
            .clip(RoundedCornerShape(20))
            .fillMaxWidth(),


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
