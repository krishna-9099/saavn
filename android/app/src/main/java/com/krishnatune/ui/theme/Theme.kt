package com.krishnatune.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext

private val FallbackDarkColorScheme = darkColorScheme(
    primary = DefaultThemeColor,
    secondary = DefaultThemeColor,
    tertiary = DefaultThemeColor,
    background = MetroDarkBackground,
    surface = MetroDarkSurface,
    onPrimary = WhiteText,
    onBackground = MetroDarkOnSurface,
    onSurface = MetroDarkOnSurface
)

private val FallbackLightColorScheme = lightColorScheme(
    primary = DefaultThemeColor,
    secondary = DefaultThemeColor,
    tertiary = DefaultThemeColor,
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
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val useSystemDynamicColor =
        themeColor == DefaultThemeColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val baseColorScheme = when {
        useSystemDynamicColor -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        themeColor != DefaultThemeColor -> {
            if (darkTheme) {
                FallbackDarkColorScheme.copy(
                    primary = themeColor,
                    secondary = themeColor.copy(alpha = 0.9f),
                    tertiary = themeColor.copy(alpha = 0.8f),
                    onPrimary = if (themeColor.luminance() > 0.45f) Color.Black else WhiteText
                )
            } else {
                FallbackLightColorScheme.copy(
                    primary = themeColor,
                    secondary = themeColor.copy(alpha = 0.9f),
                    tertiary = themeColor.copy(alpha = 0.8f),
                    onPrimary = if (themeColor.luminance() > 0.45f) Color.Black else WhiteText
                )
            }
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
