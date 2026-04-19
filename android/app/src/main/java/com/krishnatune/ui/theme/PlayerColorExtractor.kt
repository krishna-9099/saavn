package com.krishnatune.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlayerColorExtractor {
    suspend fun extractGradientColors(
        palette: Palette,
        fallbackColor: Int
    ): List<Color> = withContext(Dispatchers.Default) {
        val colorCandidates = listOfNotNull(
            palette.dominantSwatch,
            palette.vibrantSwatch,
            palette.darkVibrantSwatch,
            palette.lightVibrantSwatch,
            palette.mutedSwatch,
            palette.darkMutedSwatch,
            palette.lightMutedSwatch
        )

        val bestSwatch = colorCandidates.maxByOrNull { calculateColorWeight(it) }
        val fallbackDominant = palette.dominantSwatch?.rgb?.let(::Color)
            ?: Color(palette.getDominantColor(fallbackColor))

        val primaryColor = if (bestSwatch != null) {
            val candidateColor = Color(bestSwatch.rgb)
            if (isColorVibrant(candidateColor)) {
                enhanceColorVividness(candidateColor, 1.3f)
            } else {
                enhanceColorVividness(fallbackDominant, 1.1f)
            }
        } else {
            enhanceColorVividness(fallbackDominant, 1.1f)
        }

        listOf(
            primaryColor,
            primaryColor.copy(
                red = (primaryColor.red * 0.6f).coerceAtLeast(0f),
                green = (primaryColor.green * 0.6f).coerceAtLeast(0f),
                blue = (primaryColor.blue * 0.6f).coerceAtLeast(0f)
            ),
            Color.Black
        )
    }

    private fun isColorVibrant(color: Color): Boolean {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(color.toArgb(), hsv)
        val saturation = hsv[1]
        val brightness = hsv[2]
        return saturation > 0.25f && brightness > 0.2f && brightness < 0.9f
    }

    private fun enhanceColorVividness(color: Color, saturationFactor: Float): Color {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(color.toArgb(), hsv)
        hsv[1] = (hsv[1] * saturationFactor).coerceAtMost(1f)
        hsv[2] = (hsv[2] * 0.9f).coerceIn(0.4f, 0.85f)
        return Color(android.graphics.Color.HSVToColor(hsv))
    }

    private fun calculateColorWeight(swatch: Palette.Swatch?): Float {
        if (swatch == null) return 0f

        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(swatch.rgb, hsv)
        val saturation = hsv[1]
        val brightness = hsv[2]
        val populationWeight = swatch.population * 2f
        val vibrancyBonus = if (saturation > 0.3f && brightness > 0.3f) 1.5f else 1f

        return populationWeight * vibrancyBonus * (saturation + brightness) / 2f
    }
}
