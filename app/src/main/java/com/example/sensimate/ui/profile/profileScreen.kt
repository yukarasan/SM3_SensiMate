package com.example.sensimate.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.R
import com.example.sensimate.model.manropeFamily

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(83, 58, 134, 255),
                    0.7f to Color(22, 26, 30)
                )
            ),
        contentPadding = PaddingValues(bottom = 80.dp, top = 20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LogoutButton()
                EditButton()
            }
        }
        item { DefaultImageButton() }
        item { ProfileName() }
        item { ProfileMail() }

        // TODO: Make as list of items instead:
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
        item { UpcomingEvent() }
    }
}

@Composable
private fun LogoutButton() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(239, 112, 103)),
        modifier = Modifier
            .height(40.dp)
            .padding(start = 20.dp)
    ) {
        Text(
            text = "Log out",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
private fun EditButton() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(100),
        border = BorderStroke(3.dp, Color(199, 242, 219)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(239, 112, 103),
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .padding(end = 20.dp)
    ) {
        Text(
            text = "Edit",
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
private fun DefaultImageButton() {
    val image = painterResource(id = R.drawable.profilepic)

    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .size(180.dp)
            .padding(5.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(1f)
        )
    }
}

@Composable
private fun ProfileName() {
    Text(
        text = "Hans Jensen",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        color = Color.White,
        modifier = Modifier.padding(bottom = 5.dp)
    )
}

@Composable
private fun ProfileMail() {
    Text(
        text = "hansjensen@gmail.com",
        fontFamily = manropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp,
        color = Color(199, 242, 219),
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun UpcomingEvent() {
    Card(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .fillMaxWidth()
            .height(80.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(red = 44, green = 44, blue = 59)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Coca Cola",
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.beverages),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(60.dp)
            )
        }
    }
}