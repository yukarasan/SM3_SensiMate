package com.example.sensimate.ui.home

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.db
import com.example.sensimate.ui.navigation.Screen

@Composable
fun EventScreen(navController: NavController) {
    Column(
        modifier = Modifier.background(
            Brush.verticalGradient(
                0.0f to Color(83, 58, 134, 255),
                0.7f to Color(22, 26, 30)
            )
        )
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 20.dp),
        ) {
            item {
                ProfileLogo(
                    modifier = Modifier
                        .size(95.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp)
                        .clickable(enabled = true,
                            onClickLabel = "profile",
                            onClick = {
                                navController.navigate(Screen.ProfileScreen.route)
                            }
                        )
                )
            }
            item { QuickEntry() }

            items(Database.getListOfEvents()) { event ->
                EventCard(
                    title = event.title,
                    distance = event.distanceToEvent,
                    address = event.address,
                    onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                )
            }
        }
    }
}

@Composable
private fun ProfileLogo(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.person_circle)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier,
        alignment = Alignment.CenterEnd
    )
}