package com.krishnatune.ui.component

import androidx.compose.foundation.background
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import com.krishnatune.ui.theme.bbh_bartle
import com.krishnatune.ui.theme.rememberAdaptiveTypeScale
import com.krishnatune.ui.theme.MiniPlayerBottomSpacing
import com.krishnatune.ui.theme.MiniPlayerHeight
import com.krishnatune.ui.theme.MiniPlayerBorder
import com.krishnatune.ui.theme.MiniPlayerOverlay
import com.krishnatune.ui.theme.MiniPlayerSurface
import com.krishnatune.ui.theme.PlayerTrackBackground
import com.krishnatune.ui.theme.PlayerTrackDot
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun MiniPlayer(song: Song, onClick: () -> Unit) {
    var isPlaying by rememberSaveable(song.id) { mutableStateOf(true) }
    var progress by rememberSaveable(song.id) { mutableStateOf(0.18f) }
    val typeScale = rememberAdaptiveTypeScale()

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

    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = MiniPlayerBottomSpacing)
            .fillMaxWidth()
            .height(MiniPlayerHeight)
            .clip(RoundedCornerShape(32.dp))
            .clickable { onClick() },
        color = MiniPlayerSurface,
        shape = RoundedCornerShape(32.dp),
        tonalElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MiniPlayerProgressButton(
                song = song,
                isPlaying = isPlaying,
                progress = progress,
                onTogglePlay = { isPlaying = !isPlaying }
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = bbh_bartle,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = (17f * typeScale.title).sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = bbh_bartle,
                        fontSize = (14f * typeScale.body).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.PlaylistAdd,
                    contentDescription = stringResource(id = R.string.cd_mini_player_add_to_playlist)
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.cd_player_like)
                )
            }
        }
    }
}

@Composable
private fun MiniPlayerProgressButton(
    song: Song,
    isPlaying: Boolean,
    progress: Float,
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
                    color = PlayerTrackBackground,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = Size(diameter, diameter),
                    style = stroke
                )

                drawArc(
                    color = PlayerTrackDot,
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
                .background(MiniPlayerOverlay)
                .border(width = 1.dp, color = MiniPlayerBorder, shape = CircleShape)
                .clickable(onClick = onTogglePlay)
        ) {
            AsyncImage(
                model = song.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (!isPlaying) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MiniPlayerOverlay),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = stringResource(id = R.string.cd_mini_player_play),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = stringResource(id = R.string.cd_mini_player_pause),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}