package com.example.sensimate.ui.survey


import android.media.Image
import android.renderscript.ScriptGroup
import androidx.compose.foundation.*
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.InitialStartPage.MyTextField
import com.example.sensimate.ui.startupscreens.signUp.textFieldWithImage
import com.example.sensimate.ui.theme.*

@Composable
fun Survey2(navController: NavController) {
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
        OrangeBackButton({navController.navigate(Screen.ExtendedEventScreen.route) })
        ProgressPreview()
        Question(title = "Question 2/4")
        SurveyTitle(title = "How likely would you buy Coca Cola?")
        Information2({})

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
        ) {
            PreviousButton(onClick = { navController.navigate(Screen.Survey.route) } )
            NextButton(onClick = { navController.navigate(Screen.Survey3.route) } )
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
        progress = 0.50f //TODO:  Needs state hoisting in future.

    )
}


@Composable
fun Information2(onClick: () -> Unit) {
    val checkedState = remember { mutableStateOf(false) }
    var isClicked = remember { mutableStateOf(false )}
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
                    .padding(start = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckView()
                InformationVeryLikely(title = "Very Likely")
                Spacer(modifier = Modifier.width((120.dp)))

            }

            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckView()
                InformationLikely(title = "Likely")
                Spacer(modifier = Modifier.width((120.dp)))

            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckView()
                InformationNeutral(title = "Neutral")
                Spacer(modifier = Modifier.width((120.dp)))

            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckView()
                InformationUnlikely(title = "Unlikely")
                Spacer(modifier = Modifier.width((120.dp)))

            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckView()
                InformationVeryUnlikely(title = "Very Unlikely")
                Spacer(modifier = Modifier.width((120.dp)))


            }

        }
    }
}

@Composable
private fun InformationVeryLikely(title: String, modifier: Modifier = Modifier) {
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
private fun InformationLikely(title: String, modifier: Modifier = Modifier) {
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
private fun InformationNeutral(title: String, modifier: Modifier = Modifier) {
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
private fun InformationUnlikely(title: String, modifier: Modifier = Modifier) {
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
private fun InformationVeryUnlikely(title: String, modifier: Modifier = Modifier) {
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

fun RoundedCheckView() {

    val isChecked = remember { mutableStateOf(true) }
    val circleSize = remember { mutableStateOf(22.dp) }
    val circleSize2 = remember { mutableStateOf(12.dp) }
    val circleThickness = remember { mutableStateOf(2.dp) }
    val color = remember { mutableStateOf(GreyColor) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .toggleable(value = isChecked.value, role = Role.Checkbox) {
                isChecked.value = it
                if (isChecked.value) {
                    circleSize.value = 22.dp
                    circleThickness.value = 2.dp
                    color.value = lightpurple
                } else {
                    circleSize.value = 22.dp
                    circleThickness.value = 2.dp
                    color.value = GreyColor
                }
            }) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(22.dp)
                .background(GreyColor)
                .padding(2.dp)
                .clip(CircleShape)
                .background(darkbluegrey) ,
            contentAlignment = Center )
         {
            if (isChecked.value) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(12.dp)
                        .background(GreyColor)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(lightpurple)
                        .clickable(onClick = { isChecked.value = false
                        })
                )
            }
        }

    }
/*
    @Composable
    fun RoundedCheckView(
        onClick: () -> Unit,
        text: String,
        icon: Image,
        textStyle: TextStyle,
        iconColor: Color
    ) {
        val clicked = remember { mutableStateOf(false) }

        if (!clicked.value) {
            Box(
                modifier = Modifier
                    .clickable(onClick = {
                        clicked.value = true
                        onClick()
                    })
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colors.onSurface
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .ripple(bounded = false)
            ) {
                Row(
                    verticalAlignment = CenterVertically
                ) {
                    icon(iconColor)
                    Text(
                        text = text,
                        style = textStyle
                    )
                }
            }
        }
    }

 */

}









