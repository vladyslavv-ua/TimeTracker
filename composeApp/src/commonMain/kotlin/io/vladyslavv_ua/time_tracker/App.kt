package io.vladyslavv_ua.time_tracker

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.vladyslavv_ua.time_tracker.navigation.RootNavigation
import io.vladyslavv_ua.time_tracker.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppTheme {
        RootNavigation(navController)
    }
}