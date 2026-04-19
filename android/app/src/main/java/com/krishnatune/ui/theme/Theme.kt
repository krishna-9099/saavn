package com.krishnatune.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private val FallbackDarkColorScheme = darkColorScheme(
    primary = MetroThemeSeed,
    secondary = MetroThemeSeed,
    tertiary = MetroThemeSeed,
    background = MetroDarkBackground,
    surface = MetroDarkSurface,
    onPrimary = WhiteText,
    onBackground = MetroDarkOnSurface,
    onSurface = MetroDarkOnSurface
)

private val FallbackLightColorScheme = lightColorScheme(
    primary = MetroThemeSeed,
    secondary = MetroThemeSeed,
    tertiary = MetroThemeSeed,
    background = MetroLightBackground,
    surface = MetroLightSurface,
    onPrimary = WhiteText,
    onBackground = MetroLightOnSurface,
    onSurface = MetroLightOnSurface
)

@Composable
fun KrishnaTuneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val baseColorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> FallbackDarkColorScheme
        else -> FallbackLightColorScheme
    }

    val colorScheme = remember(baseColorScheme, darkTheme, pureBlack) {
        if (darkTheme && pureBlack) {
            baseColorScheme.copy(surface = MetroPureBlack, background = MetroPureBlack)
        } else {
            baseColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}