package com.example.sensimate.ui.survey

import androidx.compose.foundation.*
import com.example.sensimate.ui.components.OrangeBackButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.model.manropeFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.navigation.NavController
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.theme.*


@Composable
fun Survey2(navController: NavController) {
    var selectedOption by remember { mutableStateOf(0) }
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
        OrangeBackButton({navController.navigate(Screen.EventScreen.route)})
        ProgressPreview()
        Question(title = "Question 2/4")
        SurveyTitle(title = "How likely would you buy Coca Cola?")
        //Information2(title)

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
fun Information2(title: String) {
    // Add a state variable to track the selected option
    var selectedOption by remember { mutableStateOf(0) }
    var listener: ((option: Int, value: Boolean) -> Unit)? = { i: Int, b: Boolean ->
        selectedOption = i
    }
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
                // Pass the selectedOption state variable as a parameter to RoundedCheckView
                RoundedCheckView(listener, selectedOption, option = 0)
                Option(title)
                Spacer(modifier = Modifier.width((120.dp)))

            }

            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pass the selectedOption state variable as a parameter to RoundedCheckView
                RoundedCheckView(listener, selectedOption, option = 1)
                Option(title)
                Spacer(modifier = Modifier.width((120.dp)))

            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pass the selectedOption state variable as a parameter to RoundedCheckView
                RoundedCheckView(listener, selectedOption, option = 2)
                Option(title)
                Spacer(modifier = Modifier.width((120.dp)))


            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pass the selectedOption state variable as a parameter to RoundedCheckView
                RoundedCheckView(listener, selectedOption, option = 3)
                Option(title)
                Spacer(modifier = Modifier.width((120.dp)))
            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pass the selectedOption state variable as a parameter to RoundedCheckView
                RoundedCheckView(listener, selectedOption, option = 4)
                Option(title)
                Spacer(modifier = Modifier.width((120.dp)))
            }
        }
    }
}

@Composable
fun RoundedCheckView(listener: ((Int, Boolean)-> Unit)? = null, state: Int, option: Int) {
    // Add a state variable to track whether the checkbox is checked
    val isChecked = (state == option)
    val circleSize = remember { mutableStateOf(22.dp) }
    val circleSize2 = remember { mutableStateOf(12.dp) }
    val circleThickness = remember { mutableStateOf(2.dp) }
    val color = remember { mutableStateOf(GreyColor) }

    if (isChecked) {
        circleSize.value = 22.dp
        circleThickness.value = 2.dp
        color.value = lightpurple
    }
    else {
    circleSize.value = 22.dp
    circleThickness.value = 2.dp
    color.value = GreyColor
    }

        Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            // Add a clickable modifier to the Row element
            .clickable(onClick = {
                // Update the isChecked state variable
              //  isChecked.value = !isChecked.value

                listener?.invoke(option, isChecked)
            })
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(22.dp)
                .background(if(isChecked) color.value else GreyColor)
                .padding(2.dp)
                .clip(CircleShape)
                .background(darkbluegrey) ,
            contentAlignment = Center
        ) {
             if (isChecked || state == option) {
                Box(
                    modifier = Modifier
                        .size(circleSize2.value)
                        .clip(CircleShape)
                        .background(color.value)
                )
            }
        }
    }
}





/*
@Composable
fun Information2() {
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

 */

@Composable
private fun Option(title: String, modifier: Modifier = Modifier) {
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

/*
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

 */
/*
@Composable
fun RoundedCheckView(selectedOption: MutableState<Int>, optionId: Int) {
    // Add a state variable to track whether the current RoundedCheckView is checked
    val isChecked = remember { mutableStateOf(false) }
    val circleSize = remember { mutableStateOf(22.dp) }
    val circleSize2 = remember { mutableStateOf(12.dp) }
    val circleThickness = remember { mutableStateOf(2.dp) }
    val color = remember { mutableStateOf(GreyColor) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            // Add a clickable modifier to the Row element
            .clickable(onClick = {
                selectedOption.value = optionId // Update the selectedOption state variable
                isChecked.value = !isChecked.value
                if (isChecked.value) {
                    circleSize.value = 22.dp
                    circleThickness.value = 2.dp
                    color.value = lightpurple
                } else {
                    circleSize.value = 22.dp
                    circleThickness.value = 2.dp
                    color.value = GreyColor
                }
            })
    ) {
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
                circleSize.value = 22.dp
                circleThickness.value = 2.dp
                color.value = lightpurple
              /*  circle(
                    modifier = Modifier.size(circleSize2).background(color),
                    stroke = circleThickness,
                    strokeColor = lightpurple
                )

               */
            }
        }
    }
}

 */


/*
@Composable
fun RoundedCheckView(selectedOption: ModifierLocal<Int>) {
    val isChecked = remember { mutableStateOf(false) }
    val circleSize = remember { mutableStateOf(22.dp) }
    val circleSize2 = remember { mutableStateOf(12.dp) }
    val circleThickness = remember { mutableStateOf(2.dp) }
    val color = remember { mutableStateOf(GreyColor) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            // Add a clickable modifier to the Row element
            .clickable(onClick = {
                selectedOption.value = 0 // Update the selectedOption state variable
                isChecked.value = !isChecked.value
                if (isChecked.value) {
                    circleSize.value = 22.dp
                    circleThickness.value = 2.dp
                    color.value = lightpurple
                } else {
                    circleSize.value = 22.dp
                    circleThickness.value = 2.dp
                    color.value = GreyColor
                }
            })
    ) {
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
                )
            }

        }

    }
}

 */



/*
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
                )
            }

        }

    }
}

 */














