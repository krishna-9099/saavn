package com.krishnatune.ui.theme

import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.MaterialTheme
import androidx.palette.graphics.Palette
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Immutable
data class PlayerPalette(
    val gradientColors: List<Color>,
    val contentColor: Color,
    val secondaryContentColor: Color,
    val surfaceColor: Color,
    val outlineColor: Color,
    val accentContainerColor: Color,
    val accentContentColor: Color,
)

@Composable
fun rememberPlayerPalette(imageUrl: String?): PlayerPalette {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val fallbackGradient = remember(colorScheme) {
        listOf(
            colorScheme.primary.copy(alpha = 0.9f),
            colorScheme.tertiary.copy(alpha = 0.55f),
            colorScheme.background
        )
    }
    var gradientColors by remember(imageUrl, fallbackGradient) { mutableStateOf(fallbackGradient) }

    LaunchedEffect(imageUrl, colorScheme.primary) {
        if (imageUrl.isNullOrBlank()) {
            gradientColors = fallbackGradient
            return@LaunchedEffect
        }

        val extractedColors = withContext(Dispatchers.IO) {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .build()
            val drawable = runCatching { context.imageLoader.execute(request).drawable }.getOrNull()
            val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: return@withContext null
            val palette = Palette.from(bitmap)
                .maximumColorCount(16)
                .clearFilters()
                .generate()

            PlayerColorExtractor.extractGradientColors(
                palette = palette,
                fallbackColor = colorScheme.primary.toArgb()
            )
        }

        gradientColors = extractedColors ?: fallbackGradient
    }

    val leadColor = gradientColors.firstOrNull() ?: colorScheme.primary
    val useLightContent = leadColor.luminance() < 0.45f

    return remember(gradientColors, useLightContent, colorScheme) {
        PlayerPalette(
            gradientColors = gradientColors,
            contentColor = if (useLightContent) Color.White else colorScheme.onSurface,
            secondaryContentColor = if (useLightContent) {
                Color.White.copy(alpha = 0.72f)
            } else {
                colorScheme.onSurfaceVariant
            },
            surfaceColor = if (useLightContent) {
                Color.White.copy(alpha = 0.12f)
            } else {
                colorScheme.surface.copy(alpha = 0.92f)
            },
            outlineColor = if (useLightContent) {
                Color.White.copy(alpha = 0.25f)
            } else {
                colorScheme.outline.copy(alpha = 0.35f)
            },
            accentContainerColor = if (useLightContent) Color.White else colorScheme.primary,
            accentContentColor = if (useLightContent) Color.Black else colorScheme.onPrimary,
        )
    }
}
