package com.krishnatune.ui.theme

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@Immutable
data class AdaptiveTypeScale(
    val heading: Float,
    val title: Float,
    val body: Float,
    val label: Float,
)

@Composable
fun rememberAdaptiveTypeScale(): AdaptiveTypeScale {
    val config = LocalConfiguration.current
    val widthDp = config.screenWidthDp
    val fontScale = config.fontScale

    return remember(widthDp, fontScale) {
        val base = when {
            widthDp < 360 -> AdaptiveTypeScale(
                heading = 0.84f,
                title = 0.88f,
                body = 0.9f,
                label = 0.88f,
            )

            widthDp < 480 -> AdaptiveTypeScale(
                heading = 0.9f,
                title = 0.94f,
                body = 0.96f,
                label = 0.94f,
            )

            widthDp < 600 -> AdaptiveTypeScale(
                heading = 0.96f,
                title = 0.98f,
                body = 1f,
                label = 0.98f,
            )

            widthDp < 840 -> AdaptiveTypeScale(
                heading = 0.9f,
                title = 0.93f,
                body = 0.95f,
                label = 0.93f,
            )

            else -> AdaptiveTypeScale(
                heading = 0.94f,
                title = 0.96f,
                body = 0.97f,
                label = 0.96f,
            )
        }

        val fontScaleCompensation = when {
            fontScale >= 1.3f -> 0.86f
            fontScale >= 1.15f -> 0.92f
            else -> 1f
        }

        val isSmx115Tablet =
            Build.MODEL.equals("SM-X115", ignoreCase = true) &&
                Build.MANUFACTURER.equals("samsung", ignoreCase = true)
        val deviceCompensation = if (isSmx115Tablet && widthDp >= 600) 0.84f else 1f

        fun scaled(value: Float): Float {
            return (value * fontScaleCompensation * deviceCompensation).coerceIn(0.72f, 1.0f)
        }

        AdaptiveTypeScale(
            heading = scaled(base.heading),
            title = scaled(base.title),
            body = scaled(base.body),
            label = scaled(base.label),
        )
    }
}
