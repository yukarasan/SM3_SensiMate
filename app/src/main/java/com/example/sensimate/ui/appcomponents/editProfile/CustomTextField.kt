package com.example.sensimate.ui.appcomponents.editProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sensimate.model.manropeFamily

@Composable
fun CustomTextField(
    text: String,
    description: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, top = 10.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column() {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                text = description,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.White
            )
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = manropeFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                decorationBox = { innerTextField ->
                    Row() {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontFamily = manropeFamily,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            )
                        }
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(Color(154, 107, 254)),
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 2.dp),
                maxLines = 1,
                singleLine = true
            )
            Divider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}