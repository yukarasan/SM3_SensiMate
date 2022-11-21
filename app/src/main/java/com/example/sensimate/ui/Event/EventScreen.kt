package com.example.sensimate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sensimate.R
import com.example.sensimate.navigation.Screen

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
            /*
            items(data) { item ->   // TODO: Use the commented version in the future:
                Item(item)
            }
             */

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
            item {
                EventCard(
                    title = "Coca Cola",
                    distance = "2 km",
                    address = "The Circular Lab",
                    progress = 0.5f,
                    onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                )
            }
            item {
                EventCard(
                    title = "Coca Cola light med ost og kage",
                    distance = "349991 km",
                    address = "The Cicular lab at Roskilde universitet",
                    progress = 0.1f,
                    onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                )
            }
            item {
                EventCard(
                    title = "Coca Cola 1",
                    distance = "2 km",
                    address = "The Circular Lab",
                    progress = 1.0f,
                    onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                )
            }
            item {
                EventCard(
                    title = "Coca Cola 2",
                    distance = "2 km",
                    address = "The Circular Lab",
                    progress = 0.0f,
                    onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                )
            }
            item {
                EventCard(
                    title = "Coca Cola 3",
                    distance = "2 km",
                    address = "The Circular Lab",
                    progress = 0.7f,
                    onClick = { navController.navigate(Screen.ExtendedEventScreen.route) }
                )
            }
            item {
                EventCard(
                    title = "Coca Cola 4",
                    distance = "2 km",
                    address = "The Circular Lab",
                    progress = 0.3f,
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