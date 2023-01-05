package com.example.sensimate.ui.survey



import android.renderscript.ScriptGroup
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.startupscreens.signUp.textFieldWithImage
import com.example.sensimate.ui.theme.*


@Composable
fun Survey4(title: String, navController: NavController) {
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
    LazyColumn(){
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
            ) {
                OrangeBackButton({navController.navigate(Screen.EventScreen.route)})
                ProgressPreview()
                Question(title)
                SurveyTitle(title)
                Information4(options = listOf("Tomato", "Watermelon", "Cherry", "Vanilla"))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp)
                ) {
                    PreviousButton(onClick = { navController.navigate(Screen.Survey3.route) } )
                    NextButton(onClick = { navController.navigate(Screen.Survey4.route) } )
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
        progress = 1f //TODO:  Needs state hoisting in future.

    )
}


@Composable
fun Information4(options: List<String>) {
    val checkedState = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Column {
            options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 25.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CheckBox()
                    Option(title = option)
                    Spacer(modifier = Modifier.width((120.dp)))
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier
                        .padding(start = 0.dp, top = 0.dp),
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                    },
                    colors = CheckboxDefaults
                        .colors(
                            uncheckedColor = GreyColor,
                            checkmarkColor = lightpurple,
                            checkedColor = lightpurple,
                            disabledColor = darkbluegrey,
                            disabledIndeterminateColor = GreyColor,
                        )
                )
                Option(title = "Other: ")
                Spacer(modifier = Modifier.width((60.dp)))
                var other by remember { mutableStateOf("") }
                MyTextField(
                    text = other,
                    textSize = 10,
                    onValueChange = {other = it} ,
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
            .padding(top = 0.dp, start = 20.dp, bottom = 5.dp)
    )
}


@Composable
fun CheckBox() {
    //val isChecked = remember { mutableStateOf(false) }
    val checkedState = remember { mutableStateOf(false) }

    //if (isChecked.value) {
            Checkbox(
                modifier = Modifier
                    .padding(start = 0.dp, top = 0.dp),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                },
                colors = CheckboxDefaults
                    .colors(
                        uncheckedColor = GreyColor,
                        checkmarkColor = lightpurple,
                        checkedColor = lightpurple,

                        disabledColor = darkbluegrey,
                        disabledIndeterminateColor = GreyColor,
                    )
            )
}






