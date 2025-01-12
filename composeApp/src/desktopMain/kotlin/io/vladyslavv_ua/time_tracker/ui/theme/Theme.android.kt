package io.vladyslavv_ua.time_tracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.ui.theme.AppTypography

@Composable
actual fun AppTheme(darkTheme: Boolean, dynamicColor: Boolean, content: @Composable() () -> Unit) {
    val colorScheme = when {

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}