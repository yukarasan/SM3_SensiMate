package com.example.sensimate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sensimate.ui.navigation.SetupNavGraph
import com.example.sensimate.ui.Event.EventViewModel
import com.example.sensimate.ui.theme.SensimateTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(
    appViewModel: AppViewModel = viewModel(),
    eventViewModel: EventViewModel = EventViewModel(),
    modifier: Modifier = Modifier
) {
    // ViewModels
    val eventUiState by eventViewModel.eventUiState.collectAsState()
    // val profileUiState by profileViewModel.profileUiState.collectAsState()
    // val osv...

    // Navigation
    val navController: NavHostController = rememberNavController()
    SetupNavGraph(navController = navController, eventUIState = eventUiState)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    SensimateTheme {
        App()
    }
}