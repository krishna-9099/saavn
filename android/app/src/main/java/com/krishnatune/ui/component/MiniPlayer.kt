package com.krishnatune.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlaylistAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishnatune.R
import com.krishnatune.models.Song
import com.krishnatune.ui.theme.MiniPlayerBottomSpacing
import com.krishnatune.ui.theme.MiniPlayerHeight
import com.krishnatune.ui.theme.PlayerPalette
import com.krishnatune.ui.theme.rememberPlayerPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MiniPlayer(song: Song, onClick: () -> Unit) {
    var isPlaying by rememberSaveable(song.id) { mutableStateOf(true) }
    var progress by rememberSaveable(song.id) { mutableStateOf(0.18f) }
    val palette = rememberPlayerPalette(song.image)

    LaunchedEffect(isPlaying, song.id) {
        if (!isPlaying) return@LaunchedEffect
        while (isActive) {
            delay(1000)
            progress = (progress + 0.01f).coerceAtMost(1f)
            if (progress >= 1f) {
                isPlaying = false
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = MiniPlayerBottomSpacing)
            .fillMaxWidth()
            .height(MiniPlayerHeight)
            .clip(RoundedCornerShape(32.dp))
            .background(Brush.horizontalGradient(palette.gradientColors))
            .border(1.dp, palette.outlineColor, RoundedCornerShape(32.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.22f))
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MiniPlayerProgressButton(
                song = song,
                isPlaying = isPlaying,
                progress = progress,
                palette = palette,
                onTogglePlay = { isPlaying = !isPlaying }
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        lineHeight = 18.sp
                    ),
                    color = palette.contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 14.sp
                    ),
                    color = palette.secondaryContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            MiniPlayerActionButton(
                icon = Icons.Outlined.PlaylistAdd,
                contentDescription = stringResource(R.string.cd_mini_player_add_to_playlist),
                palette = palette
            )

            MiniPlayerActionButton(
                icon = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.cd_player_like),
                palette = palette
            )
        }
    }
}

@Composable
private fun MiniPlayerProgressButton(
    song: Song,
    isPlaying: Boolean,
    progress: Float,
    palette: PlayerPalette,
    onTogglePlay: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .drawWithContent {
                drawContent()
                val stroke = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                val diameter = size.minDimension
                val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)

                drawArc(
                    color = palette.outlineColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = Size(diameter, diameter),
                    style = stroke
                )

                drawArc(
                    color = palette.accentContainerColor,
                    startAngle = -90f,
                    sweepAngle = 360f * progress.coerceIn(0f, 1f),
                    useCenter = false,
                    topLeft = topLeft,
                    size = Size(diameter, diameter),
                    style = stroke
                )
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.24f))
                .border(width = 1.dp, color = palette.outlineColor, shape = CircleShape)
                .clickable(onClick = onTogglePlay)
        ) {
            AsyncImage(
                model = song.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = if (isPlaying) 0.18f else 0.42f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) {
                        stringResource(R.string.cd_mini_player_pause)
                    } else {
                        stringResource(R.string.cd_mini_player_play)
                    },
                    tint = palette.contentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun MiniPlayerActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    palette: PlayerPalette
) {
    Surface(
        modifier = Modifier.size(38.dp),
        shape = CircleShape,
        color = palette.surfaceColor
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = palette.contentColor
            )
        }
    }
}
