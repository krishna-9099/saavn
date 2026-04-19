package com.krishnatune.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    background = BlackBackground,
    surface = DarkSurface,
    onPrimary = WhiteText,
    onBackground = WhiteText,
    onSurface = WhiteText
)

@Composable
fun KrishnaTuneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}