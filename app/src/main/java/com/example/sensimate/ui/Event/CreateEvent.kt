package com.example.sensimate.ui.Event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.R
import com.example.sensimate.ui.theme.*


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CreateEventScreen()
    //TextFiledTimeText()
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
    AddPhoto()
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
        modifier = Modifier.size(240.dp, 50.dp)

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
    Column(
        modifier = Modifier
            .padding(55.dp, 55.dp, 30.dp, 30.dp)
            .fillMaxSize(),
    ) {
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
            placeholder = {Text(text = "Type here...", color = Color(0xEFFF7067) )})
    }}

}

@Composable
fun TextFiledDescriptionText(){
    Column(
        modifier = Modifier
            .padding(55.dp, 150.dp, 30.dp, 30.dp)
            .fillMaxSize(),
    ) {
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
                placeholder = {Text(text = "Type here...",color = Color(0xEFFF7067))}
            )

        }}

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
fun Tester(){
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
}}

@Composable
fun AddPhoto(){
    Column(
        modifier = Modifier
            .padding(350.dp, 8.dp, 15.dp, 10.dp)
            .fillMaxSize(),
    ) {
    Image(
        painter = painterResource(id = R.drawable.ic_add_circle_outlined),
         contentDescription = "HEJ MED DIG ",
        modifier = Modifier
            .size(50.dp)
            .fillMaxSize(),

    )
    }
}
@Composable
fun TextToPhoto(){
    Column(
        modifier = Modifier
            .padding(340.dp, 49.dp, 2.dp, 1.dp)
            .fillMaxSize(),
    ){
    Text(text = "Add Photo",
    color = Color(0xFFB874A6), fontSize = 10.sp,
        maxLines = 1)
    }
}

@Composable
fun TextFiledLoctionText(){
    Column(
        modifier = Modifier
            .padding(1.dp, 65.dp, 1.dp, 2.dp)
            .fillMaxWidth()
            .fillMaxSize(),
    ) {
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
                .fillMaxWidth())
        }}

}

@Composable
fun TextFiledTimeText(){
    Column(
        modifier = Modifier
            .padding(1.dp, 5.dp, 1.dp, 2.dp)
            .fillMaxWidth()
            .fillMaxSize(),
    ) {
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
                    .fillMaxWidth())
        }}

}

