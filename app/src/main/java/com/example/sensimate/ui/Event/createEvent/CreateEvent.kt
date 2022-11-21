package com.example.sensimate.ui.Event.createEvent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.R
import com.example.sensimate.common.editProfile.CheckBox
import com.example.sensimate.navigation.Screen
import com.example.sensimate.navigation.SetupNavGraph
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.theme.*


@Preview(showBackground = true)
@Composable
fun CreateEventPreview() {
    //CreateEventScreen()
    //QuestionPageScreen()
    CreateMultpleChoiceQuestionScreen()
}



@Composable
fun CreateEventScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottonGradient
                    )
                )
            )
    )
    AddPhoto(modifier = Modifier
        .padding(345.dp, 20.dp, 2.dp, 1.dp)
        .size(20.dp), id = R.drawable.ic_add_circle_outlined
    )
    TextToPhoto()
    TextFiledTitleText()
    TextFiledDescriptionText()
    Card(
        modifier = Modifier
            .padding(start = 1.dp, end = 1.dp, top = 300.dp)
            .fillMaxWidth(),

        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xFF4D3B72)


    ){

    Image(
        painter = painterResource(
            id = R.drawable.sentimatelogo
        ),
        contentDescription = "",
        modifier = Modifier
            .size(2700.dp)
            .blur(1.dp)
            .alpha(0.2f),
        contentScale = ContentScale.Crop,

        )
        TextFiledTimeText()
        TextFiledLoctionText()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Spacer(modifier = Modifier.size(250.dp))

    Button(
        onClick = {/*TODO*/},
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
        modifier = Modifier.size(240.dp, 50.dp),
        enabled = true

    ) {
        Text(
            text = "Create Event",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = manropeFamily
        )
    }
        Spacer(modifier = Modifier.size(100.dp))
    Button(
        onClick = { /*TODO*/ },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = RedColor),
        modifier = Modifier.size(240.dp, 50.dp)
    ) {
        Text(
            text = "Go Back",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = manropeFamily
        )
    }
}
    }}


@Composable
fun TextFiledTitleText(){
        var text by remember { mutableStateOf("") }
        ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
            text = newText },
            label = {
                Text(
                    text = "Title",
                    color = Color(0xFFB874A6)
                )
                }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
        modifier = Modifier
            .padding(55.dp, 55.dp, 30.dp, 30.dp)
            )
    }
}



@Composable
fun TextFiledDescriptionText(){
        var text by remember { mutableStateOf("") }
        ContentColorComponent(contentColor = Color.White) {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText },
                label = {
                    Text(
                        text = "Description",
                        color = Color(0xFFB874A6)
                    )
                }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                singleLine = true,
                placeholder = {Text(text = "Type here...",color = Color(0xEFFF7067))},
                modifier = Modifier
                    .padding(55.dp, 150.dp, 30.dp, 30.dp)
            )

        }
}


@Composable
fun ContentColorComponent(
    contentColor: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides contentColor,
        content = content)
}



@Composable
fun AddPhoto(modifier: Modifier = Modifier,id: Int){
    IconButton(onClick = { /*TODO*/ }) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "HEJ MED DIG ",
            modifier = modifier
        )
    }
}
@Composable
fun TextToPhoto(){
    Text(text = "Add Photo",
    color = Color(0xFFB874A6), fontSize = 11.sp,
        maxLines = 1,
    modifier = Modifier
        .padding(330.dp, 44.dp, 2.dp, 1.dp)

    )
    }


@Composable
fun TextFiledLoctionText(){
         var text by remember { mutableStateOf("") }
        ContentColorComponent(contentColor = Color.White) {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText },
                label = {
                    Text(
                        text = "Location",
                        color = Color(0xFFB874A6)
                    )
                }, trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.redlocationicon),
                            modifier = Modifier
                                .size(20.dp),
                            contentDescription = "")

                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                singleLine = true,

                placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = Modifier
                .padding(1.dp, 65.dp, 1.dp, 1.dp)
                .fillMaxWidth()
            )
        }
}



@Composable
fun TextFiledTimeText(){
        var text by remember { mutableStateOf("") }
        ContentColorComponent(contentColor = Color.White) {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText },
                label = {
                    Text(
                        text = "Date and time",
                        color = Color(0xFFB874A6)
                    )
                }, trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.yellowpencil),
                            modifier = Modifier
                                .size(20.dp),
                            contentDescription = "")

                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                singleLine = true,

                placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
                modifier = Modifier
                    .padding(1.dp, 2.dp, 1.dp, 1.dp)
                    .fillMaxWidth()
                   )
        }
}

@Composable
fun TextFiledQuestionText(modifier: Modifier){
    var text by remember { mutableStateOf("") }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText },
            label = {
                Text(
                    text = "Question",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = modifier

        )
    }
}

@Composable
fun TextFiledAnswerText(modifier: Modifier){
    var text by remember { mutableStateOf("") }
    ContentColorComponent(contentColor = Color.White) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText },
            label = {
                Text(
                    text = "Answer",
                    color = Color(0xFFB874A6)
                )
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            singleLine = true,
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )},
            modifier = modifier

        )
    }
}

// figur 2

@Composable
fun QuestionPageScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottonGradient
                    )
                )
            )
    )

    Card(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 150.dp, bottom = 150.dp)
            .fillMaxWidth()
            .fillMaxSize(),

        shape = RoundedCornerShape(14.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)

    ){} //TODO
            Text(
                text = "Create your first question",
                color = Color(0xFFB874A6),
                fontSize = 20.sp,
                modifier = Modifier
                    //.padding(40.dp, 250.dp, 88.dp, 269.dp)
                    .padding(60.dp, 400.dp, 88.dp, 269.dp)
            )
            AddPhoto(modifier = Modifier
                .padding(300.dp, 405.dp, 1.dp, 1.dp)
                .size(20.dp)
                .clickable(enabled = true,
                    onClick = { /*TODO*/ }),
                id = R.drawable.redaddplus)


    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp).clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick =  { /*TODO*/ })
        ,id = R.drawable.redgobackbutton)

    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp).clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick =  { /*TODO*/ })
        , id = R.drawable.greenconfirmedbutton)


}

// Figur 3
@Composable
fun CreateMultpleChoiceQuestionScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottonGradient
                    )
                )
            )
    )
    Text(
        text = "Multiple-choice",
        color = Color(0xEFFF7067),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(125.dp, 30.dp, 88.dp, 269.dp))
    TextFiledQuestionText(modifier = Modifier
        .padding(55.dp, 130.dp, 30.dp, 30.dp))
    TextFiledAnswerText(modifier = Modifier
        .padding(55.dp, 225.dp, 30.dp, 30.dp))
    Divider(
        color = Color.White,
        thickness = 2.dp,
        modifier = Modifier.padding(1.dp,400.dp, 1.dp, 1.dp))
    Text(
        text = "Settings",
        color = Color(0xFFB874A6),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp, 410.dp, 88.dp, 269.dp)
    )
    Text(
        text = "Require an answer",
        color = Color(0xEFFF7067),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp, 450.dp, 88.dp, 269.dp)
    )

//TODO LAV EN GO BACK BUTTON
    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp).clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick =  { /*TODO*/ })
        , id = R.drawable.redgobackbutton)

    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp).clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick =  { /*TODO*/ })
        , id = R.drawable.greenconfirmedbutton)


}




