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
/**
 *  The EmployeesListScreen composable function provides the UI of the employee's list screen.
 *  it takes an AdminViewModel and NavController as a parameter, it uses the BuildProfileList
 *  function to create the list of profiles and the DeleteProfileDialog to delete a profile.
 * @author Hussein El-Zein
 * @param viewModel an instance of AdminViewModel that provides the data and logic of the screen
 * @param navController a NavController that handle the navigation to other screens
 * */
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

            Button(onClick = { navController.navigate(Screen.CreateScreenEmployee.route) }) {
                Text(text = "Add")
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
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        Spacer(modifier = Modifier.size(20.dp))
        EmployeeListTitle()
        Spacer(modifier = Modifier.size(5.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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

/**
 *  The BuildProfileList composable function provides a list of employee's profiles, it takes a list of emails,
 *  a MutableState of showDialog and a MutableState of chosenMail as parameter
 *  it uses the LazyVerticalGrid to create the grid of profiles and ShowProfile function to create each profile.
 * @author Hussein El-Zein
 * @param emails a list of emails associated with the employee profiles that should be displayed
 * @param showDialog a MutableState that is used to control the visibility of the dialog
 * @param chosenMail a MutableState that is used to store the email of the chosen profile
 */
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

@Preview(showBackground = true)
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

/**
 *  The ShowProfile composable function provides a button that displays the email
 *  of an employee and takes the user to the employee's profile when clicked
 *  it takes an email, and a callback function as a parameter.
 * @author Hussein El-Zein
 * @param mail the email associated with the employee that should be displayed
 * @param onClick callback function that will be called when the button is clicked
 */
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

/**
 *  The DeleteProfileDialog composable function provides a dialog that allows the admin to delete an employee profile
 *  it takes an email, showDialog and adminViewModel as a parameter and it uses the adminViewModel to delete the profile.
 * @author Hussein El-Zein
 * @param email the email associated with the profile that should be deleted
 * @param showDialog a MutableState that is used to control the visibility of the dialog
 * @param adminViewModel the viewModel that provides the functionality to delete the profile
 */
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
                stringResource(id = R.string.deleteEmpTitle),
                style = typography.h6
            )
        },
        text = {
            Text(
                 stringResource(id = R.string.deleteEmpSure) +" $email ?",
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
                    Text(text = stringResource(id = R.string.confirm), color = Color.White)
                }
                Button(
                    { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF707070))
                ) {
                    // Close the dialog
                    Text(text = stringResource(id = R.string.cancel), color = Color.White)
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

/**
 *  The EmployeeListTitle composable function provides a title and a description for the employee list screen
 *  it will be rendered in the top of employee list
 * @author Hussein El-Zein
 */
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

            Column(modifier = Modifier.padding(start = 6.dp)) {
                Text(
                    stringResource(id = R.string.aListTitle),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            }

        }
        Box(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
                .background(color = Color.White.copy(alpha = 0.1f))
        ) {
            Text(
                stringResource(id = R.string.aList),
                style = TextStyle(color = Color.White, fontSize = 14.sp)
            )
        }
    }

}
