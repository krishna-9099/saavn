package com.krishnatune.ui.player

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.krishnatune.R
import com.krishnatune.models.Song
import com.krishnatune.ui.theme.PlayerHorizontalPadding
import com.krishnatune.ui.theme.rememberPlayerPalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerScreen(song: Song, onClose: () -> Unit = {}) {
    val totalDurationSeconds = 196
    var progress by rememberSaveable(song.id) { mutableStateOf(4f / totalDurationSeconds) }
    var isPlaying by rememberSaveable(song.id) { mutableStateOf(true) }
    val palette = rememberPlayerPalette(song.image)

    val coroutineScope = rememberCoroutineScope()
    val offsetY = remember { Animatable(0f) }
    var screenHeight by remember { mutableStateOf(0f) }

    val elapsedSeconds = (progress * totalDurationSeconds)
        .roundToInt()
        .coerceIn(0, totalDurationSeconds)

    val playButtonCorner by animateDpAsState(
        targetValue = if (isPlaying) 28.dp else 36.dp,
        label = "playerPlayButtonCorner"
    )

    LaunchedEffect(isPlaying, song.id) {
        if (!isPlaying) return@LaunchedEffect
        while (isActive) {
            delay(1000)
            progress = (progress + 1f / totalDurationSeconds).coerceAtMost(1f)
            if (progress >= 1f) {
                isPlaying = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { screenHeight = it.size.height.toFloat() }
            .graphicsLayer { translationY = offsetY.value }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (offsetY.value > screenHeight * 0.18f) {
                            coroutineScope.launch {
                                offsetY.animateTo(screenHeight, animationSpec = tween(260))
                                onClose()
                            }
                        } else {
                            coroutineScope.launch {
                                offsetY.animateTo(0f, animationSpec = tween(260))
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            offsetY.snapTo((offsetY.value + dragAmount.y).coerceAtLeast(0f))
                        }
                    }
                )
            }
            .background(Brush.verticalGradient(palette.gradientColors))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.12f), Color.Black.copy(alpha = 0.72f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = PlayerHorizontalPadding, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerChromeButton(
                    onClick = onClose,
                    containerColor = palette.surfaceColor,
                    contentColor = palette.contentColor
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.cd_player_close)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.player_now_playing),
                        style = MaterialTheme.typography.labelLarge,
                        color = palette.secondaryContentColor
                    )
                    Text(
                        text = stringResource(R.string.player_playlist_name),
                        style = MaterialTheme.typography.titleMedium,
                        color = palette.contentColor
                    )
                }

                PlayerChromeButton(
                    onClick = { },
                    containerColor = palette.surfaceColor,
                    contentColor = palette.contentColor
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = stringResource(R.string.cd_player_more)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                shape = RoundedCornerShape(34.dp),
                color = palette.surfaceColor,
                tonalElevation = 12.dp
            ) {
                AsyncImage(
                    model = song.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, palette.outlineColor, RoundedCornerShape(34.dp))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = song.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(iterations = Int.MAX_VALUE),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                color = palette.contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = song.artist,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = palette.secondaryContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PlayerActionChip(
                    icon = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.cd_player_share),
                    palette = palette
                )
                PlayerActionChip(
                    icon = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.cd_player_like),
                    palette = palette
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                color = palette.surfaceColor,
                tonalElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
                    Slider(
                        value = progress,
                        onValueChange = { progress = it },
                        colors = SliderDefaults.colors(
                            thumbColor = palette.accentContainerColor,
                            activeTrackColor = palette.accentContainerColor,
                            inactiveTrackColor = palette.outlineColor
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatTime(elapsedSeconds),
                            style = MaterialTheme.typography.bodyMedium,
                            color = palette.secondaryContentColor
                        )
                        Text(
                            text = formatTime(totalDurationSeconds),
                            style = MaterialTheme.typography.bodyMedium,
                            color = palette.secondaryContentColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerTransportButton(
                    icon = Icons.Filled.SkipPrevious,
                    contentDescription = stringResource(R.string.cd_player_previous),
                    palette = palette
                )

                Surface(
                    modifier = Modifier
                        .width(198.dp)
                        .height(76.dp)
                        .clip(RoundedCornerShape(playButtonCorner))
                        .clickable { isPlaying = !isPlaying },
                    shape = RoundedCornerShape(playButtonCorner),
                    color = palette.accentContainerColor
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) {
                                stringResource(R.string.cd_player_pause)
                            } else {
                                stringResource(R.string.cd_player_play)
                            },
                            tint = palette.accentContentColor,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isPlaying) stringResource(R.string.player_pause) else stringResource(R.string.player_play),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = palette.accentContentColor
                        )
                    }
                }

                PlayerTransportButton(
                    icon = Icons.Filled.SkipNext,
                    contentDescription = stringResource(R.string.cd_player_next),
                    palette = palette
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PlayerFooterButton(
                    icon = Icons.Filled.QueueMusic,
                    contentDescription = stringResource(R.string.cd_player_queue),
                    palette = palette
                )
                PlayerFooterButton(
                    icon = Icons.Filled.FormatAlignLeft,
                    contentDescription = stringResource(R.string.cd_player_lyrics),
                    palette = palette
                )
                PlayerFooterButton(
                    icon = Icons.Filled.Repeat,
                    contentDescription = stringResource(R.string.cd_player_repeat),
                    palette = palette
                )
            }
        }
    }
}

@Composable
private fun PlayerChromeButton(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.size(44.dp),
        shape = CircleShape,
        color = containerColor
    ) {
        IconButton(onClick = onClick) {
            Box(contentAlignment = Alignment.Center) {
                androidx.compose.runtime.CompositionLocalProvider(
                    androidx.compose.material3.LocalContentColor provides contentColor
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun PlayerActionChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    palette: com.krishnatune.ui.theme.PlayerPalette
) {
    Surface(
        modifier = Modifier.size(width = 82.dp, height = 52.dp),
        shape = RoundedCornerShape(18.dp),
        color = palette.surfaceColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = palette.contentColor
            )
        }
    }
}

@Composable
private fun PlayerTransportButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    palette: com.krishnatune.ui.theme.PlayerPalette
) {
    Surface(
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        color = palette.surfaceColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = palette.contentColor,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun PlayerFooterButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    palette: com.krishnatune.ui.theme.PlayerPalette
) {
    Surface(
        modifier = Modifier.size(54.dp),
        shape = RoundedCornerShape(18.dp),
        color = palette.surfaceColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = palette.secondaryContentColor
            )
        }
    }
}

private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
