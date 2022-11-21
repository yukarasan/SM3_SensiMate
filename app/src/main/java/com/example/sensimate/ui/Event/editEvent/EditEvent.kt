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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.Event.createEvent.CreateMultpleChoiceQuestionScreen
import com.example.sensimate.ui.Event.createEvent.TextFiledAnswerText
import com.example.sensimate.ui.Event.createEvent.TextFiledQuestionText
import com.example.sensimate.ui.Event.extendedEvent.ExtendedEvent
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.home.EventInputField
import com.example.sensimate.ui.survey.Survery
import com.example.sensimate.ui.survey.Survey4
import com.example.sensimate.ui.theme.BottonGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.LightColor
import com.example.sensimate.ui.theme.RedColor

@Preview(showBackground = true)
@Composable
fun EditEventPreview() {
    //EditEvent()
    //EditPage()
    EditSurvey()
    //EditSurveyPage()
}
@Composable
fun EditEvent(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple, BottonGradient
                    )
                )
            )
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 70.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(red = 44, green = 44, blue = 59)
        ) {
            Column {
                Row {
                    Column(
                        modifier = Modifier.padding(
                            top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp
                        )
                    ) {

                        Row {
                            Column {
                                Title(title = "Coca Cola")
                                Discription(
                                    discription = "Come and taste the freshing sensation " +
                                            "of Coca Cola. Get a whole six pack for free."
                                )
                            }
                            Image(
                                painter = painterResource(
                                    id = R.drawable.beverages
                                ),
                                contentDescription = "",
                                modifier = Modifier.size(145.dp),
                            )
                        }
                    }
                }
                EventInputField()
                Allergens(title = "Allergens")
                Discription(discription = "N/A")
                Spacer(modifier = Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column() {
                        Title(title = "The Circular lab")
                    }
                    Discription(discription = "30km")
                }
                Image(
                    painter = painterResource(
                        id = R.drawable.location
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                )
                Discription(discription = "Helsingørmotervejen 15, 2500 lyngby")
                Bar(progress = 0.39f)
            }
        }
        Spacer(modifier = Modifier.size(25.dp))
        EditButton("Edit Survey")
    }

    AddPhoto(
        modifier = Modifier
            .padding(15.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ })
                , id = R.drawable.redgobackbutton)


    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ })
        , id = R.drawable.yelloweditbutton)
}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)
    )
}

@Composable
private fun Discription(discription: String, modifier: Modifier = Modifier) {
    Text(
        text = discription,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)

    )
}

@Composable
private fun Allergens(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 8.dp)
            .width(220.dp)
    )
}

@Composable
private fun Bar(progress: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(30.dp)),
            backgroundColor = Color(red = 63, green = 69, blue = 81),
            color = Color(red = 199, green = 242, blue = 219), //progress color
            progress = progress //TODO:  Needs state hoisting in future.
        )
    }
}

@Composable
private fun EditButton(title: String) {
    Button(
        onClick = { /*TODO*/ },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = LightColor),
        modifier = Modifier.size(345.dp, 60.dp),

        ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.White,
            fontFamily = manropeFamily
        )
    }
}
@Composable
fun AddPhoto(modifier: Modifier = Modifier,id: Int){
        Image(
            painter = painterResource(id = id),
            contentDescription = "HEJ MED DIG ",
            modifier = modifier
        )
}

@Composable
fun EditPage(){
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD7D123)),
                modifier = Modifier.size(240.dp, 50.dp),
                enabled = true

            ) {
                Text(
                    text = "Confirmed Edit",
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
    var text by remember { mutableStateOf("Coca Cola") }
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
    var text by remember { mutableStateOf("Come and taste the freshing sensation of Coca Cola. Get a whole six pack for free.") }
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
fun TextToPhoto(){
    Text(text = "Edit Photo",
        color = Color(0xFFB874A6), fontSize = 11.sp,
        maxLines = 1,
        modifier = Modifier
            .padding(330.dp, 44.dp, 2.dp, 1.dp)

    )
}


@Composable
fun TextFiledLoctionText(){
    var text by remember { mutableStateOf("Helsingørmotervejen 15, 2500 lyngby") }
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
    var text by remember { mutableStateOf("??/??/?? - ??:??") }
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
fun EditSurvey(){
    Survey4()
    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ })
        , id = R.drawable.yelloweditbutton)
}

@Composable
fun EditSurveyPage(){
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
        .padding(55.dp, 130.dp, 30.dp, 30.dp),"What other flavours of Coca Cola would you like?")
    TextFiledAnswerText(modifier = Modifier
        .padding(55.dp, 225.dp, 30.dp, 30.dp),"Tomato") //TODO
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
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ }), id = R.drawable.redgobackbutton
    )

    AddPhoto(
        modifier = Modifier
            .padding(330.dp, 10.dp, 2.dp, 1.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClickLabel = "Clickable image",
                onClick = { /*TODO*/ }), id = R.drawable.greenconfirmedbutton
    )


}


