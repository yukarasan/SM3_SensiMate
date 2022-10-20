package com.example.sensimate

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sensimate.ui.home.HomeScreen
import com.example.sensimate.ui.theme.SensimateTheme

@Composable
fun App(
    // modifier: Modifier = Modifier,
    // appViewModel: AppViewModel = viewModel()
) {
    // val appUiState by appViewModel.uiState.collectAsState()
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    SensimateTheme {
        App()
    }
}