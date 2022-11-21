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
fun Survey4() {
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
        OrangeBackButton({})
        ProgressPreview()
        Question(title = "Question 4/6")
        SurveyTitle(title = "What other flavours of Coca Cola would you like?")
        Information4()

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
fun Information4() {
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
            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 0.dp),
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

                InformationTomato(title = "Tomato")
                Spacer(modifier = Modifier.width((120.dp)))
            }


            Row(
                modifier = Modifier
                    .padding(start = 0.dp, top = 25.dp),
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
                InformationWatermelon(title = "Watermelon")
                Spacer(modifier = Modifier.width((120.dp)))

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
                InformationCherry(title = "Cherry")
                Spacer(modifier = Modifier.width((120.dp)))

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
                InformationVanilla(title = "Vanilla")
                Spacer(modifier = Modifier.width((120.dp)))

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
                InformationOther(title = "Other: _____")
                Spacer(modifier = Modifier.width((120.dp)))
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
private fun InformationTomato(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 0.dp, start = 20.dp)
    )
}

@Composable
fun InformationWatermelon(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 5.dp, start = 20.dp)
    )
}

@Composable
private fun InformationCherry(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 0.dp, start = 20.dp)
    )
}

@Composable
private fun InformationVanilla(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 0.dp, start = 20.dp)
    )
}

@Composable
private fun InformationOther(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = Color.White,
        modifier = modifier
            .padding(top = 0.dp, start = 20.dp)
    )
}

/*

@Composable
fun InputField2(title: String, onValueChange: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 0.dp, top = 25.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = GreyColor
    ) {
        Column() {
            Row() {
            }

        }

    }


*/




