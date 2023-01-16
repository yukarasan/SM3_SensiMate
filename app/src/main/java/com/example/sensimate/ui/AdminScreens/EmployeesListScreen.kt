package com.example.sensimate.ui.AdminScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.R
import com.example.sensimate.data.Database
import com.example.sensimate.data.Profile
import com.example.sensimate.data.auth
import com.example.sensimate.model.manropeFamily
import com.example.sensimate.ui.InitialStartPage.showLoading
import com.example.sensimate.ui.components.OrangeBackButton
import com.example.sensimate.ui.navigation.Screen
import com.example.sensimate.ui.startupscreens.components.InitialStartBackground
import com.example.sensimate.ui.theme.BottomGradient
import com.example.sensimate.ui.theme.DarkPurple
import com.example.sensimate.ui.theme.Purple200
import com.example.sensimate.ui.theme.PurpleButtonColor
import org.apache.poi.xddf.usermodel.chart.Shape

@Composable
fun EmployeesListScreen(
    viewModel: AdminViewModel,
    navController: NavController
) {
    val state = viewModel._uiState.collectAsState()

    val context = LocalContext.current

    val showLoading = remember {
        mutableStateOf(false)
    }

    val showDialog = remember {
        mutableStateOf(false)
    }

    val chosenMail = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(size = 300.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkPurple,
                        BottomGradient
                    )
                )
            )
    )


    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OrangeBackButton {
                navController.popBackStack()
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "tilføj")
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = if (auth.currentUser?.email != null) {
                    auth.currentUser!!.email.toString()
                } else {
                    ""
                },
                fontFamily = manropeFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = Color(199, 242, 219),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            LogoutButton(
                onClick = {
                    Database.signOut(context = context)
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            EmployeeListTitle()
            Spacer(modifier = Modifier.size(5.dp))

            if (state.value.loaded.value) {
                BuildProfileList(emails = state.value.mails, showDialog, chosenMail)
                showLoading.value = false
            } else {
                showLoading.value = true
                showLoading(showloading = showLoading)
            }
        }
    }





    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showDialog.value) {
            DeleteProfileDialog(email = chosenMail.value, showDialog, adminViewModel = viewModel)
        }
    }


}


@Composable
fun BuildProfileList(
    emails: List<String>,
    showDialog: MutableState<Boolean>,
    chosenMail: MutableState<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(emails) { index, email ->
            ShowProfile(mail = email) {
                showDialog.value = true
                chosenMail.value = email
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun PreviewBuildProfileList() {
    BuildProfileList(
        emails = listOf(
            "zz@zz.zz", "husseinelzeinprivat@gmail.com",
            "yukarasan@hotmail.com",
            "sabirinomar@gmail.com",
            "zz@zz.zz", "husseinelzeinprivat@gmail.com",
            "yukarasan@hotmail.com", "sabirinomar@gmail.com"
        ),
        remember { mutableStateOf(false) },
        remember { mutableStateOf("") }
    )
}


@Composable
fun ShowProfile(mail: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(20.dp)
            .clickable { onClick.invoke() }
    ) {

        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            color = Color(0x9FC79BE6)
        ) {}

        Text(
            text = mail,
            fontSize = 15.sp,
            color = Color.White,
            fontFamily = manropeFamily,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline
        )

    }


}


@Preview(showBackground = true)
@Composable
fun PreviewDeleteProfileDialog() {
    DeleteProfileDialog(
        email = "husseinelzeinprivat@gmail.com", remember {
            mutableStateOf(false)
        },
        AdminViewModel()
    )
}


@Composable
fun DeleteProfileDialog(
    email: String,
    showDialog: MutableState<Boolean>,
    adminViewModel: AdminViewModel
) {

    val context = LocalContext.current
    AlertDialog(
        title = {
            Text(
                "Delete Profile",
                style = typography.h6
            )
        },
        text = {
            Text(
                "Are you sure you want to delete the profile associated with the email $email ?",
                style = typography.body2
            )
        },
        buttons = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    {
                        adminViewModel.clickOnDeleteEmployee(
                            email = email,
                            context = context
                        )
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA26161))
                ) {
                    // Perform the delete action here
                    Text(text = "Confirm deletion", color = Color.White)
                }
                Button(
                    { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF707070))
                ) {
                    // Close the dialog
                    Text(text = "Cancel", color = Color.White)
                }
            }

        },
        onDismissRequest = {
            // Close the dialog
        },
        backgroundColor = Color(0xFFF7F3FF),
        shape = RoundedCornerShape(15.dp)

    )
}


@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(239, 112, 103)),
        modifier = Modifier
            .height(40.dp)
            .padding()
    ) {
        Text(
            text = stringResource(id = R.string.logOut),
            fontFamily = manropeFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmployeeListTitle() {
    Column(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF706299),
                            Color(0xFFF7EBFF)
                        )
                    )
                )
        ) {
            Text(
                "Employee List",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
                .background(color = Color.White.copy(alpha = 0.1f))
        ) {
            Text(
                "A list of all the employees in the company",
                style = TextStyle(color = Color.White, fontSize = 14.sp)
            )
        }
    }

}
