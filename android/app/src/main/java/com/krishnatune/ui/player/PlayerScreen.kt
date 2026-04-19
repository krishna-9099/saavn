package com.krishnatune.ui.player

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishnatune.R
import com.krishnatune.models.Song
import com.krishnatune.ui.theme.bbh_bartle
import com.krishnatune.ui.theme.rememberAdaptiveTypeScale
import com.krishnatune.ui.theme.PlayerArtworkCard
import com.krishnatune.ui.theme.PlayerButtonSurface
import com.krishnatune.ui.theme.PlayerControlSurface
import com.krishnatune.ui.theme.PlayerGradientBottom
import com.krishnatune.ui.theme.PlayerGradientTop
import com.krishnatune.ui.theme.PlayerIconOnLight
import com.krishnatune.ui.theme.PlayerHorizontalPadding
import com.krishnatune.ui.theme.PlayerSecondaryText
import com.krishnatune.ui.theme.PlayerTrackBackground
import com.krishnatune.ui.theme.PlayerTrackDot
import com.krishnatune.ui.theme.WhiteText
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun PlayerScreen(song: Song, onClose: () -> Unit = {}) {
    val totalDurationSeconds = 196
    var progress by rememberSaveable(song.id) { mutableStateOf(4f / totalDurationSeconds) }
    var isPlaying by rememberSaveable(song.id) { mutableStateOf(true) }
    val typeScale = rememberAdaptiveTypeScale()

    val coroutineScope = rememberCoroutineScope()
    val offsetY = remember { Animatable(0f) }
    var screenHeight by remember { mutableStateOf(0f) }

    val elapsedSeconds = (progress * totalDurationSeconds)
        .roundToInt()
        .coerceIn(0, totalDurationSeconds)

    val playButtonCorner by animateDpAsState(
        targetValue = if (isPlaying) 26.dp else 34.dp,
        label = "playButtonCorner"
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
                detectVerticalDragGestures(
                    onDragEnd = {
                        if (offsetY.value > screenHeight * 0.2f) {
                            coroutineScope.launch {
                                offsetY.animateTo(screenHeight, animationSpec = tween(280))
                                onClose()
                            }
                        } else {
                            coroutineScope.launch {
                                offsetY.animateTo(0f, animationSpec = tween(280))
                            }
                        }
                    },
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            val nextOffset = (offsetY.value + dragAmount).coerceAtLeast(0f)
                            offsetY.snapTo(nextOffset)
                        }
                    }
                )
            }
            .background(
                brush = Brush.verticalGradient(colors = listOf(PlayerGradientTop, PlayerGradientBottom))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = PlayerHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.cd_player_close),
                        tint = WhiteText,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.player_now_playing),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontFamily = bbh_bartle,
                            fontSize = (16f * typeScale.label).sp
                        ),
                        color = PlayerSecondaryText,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(id = R.string.player_playlist_name),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = bbh_bartle,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = (22f * typeScale.title).sp
                        ),
                        color = WhiteText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = stringResource(id = R.string.cd_player_more),
                        tint = WhiteText,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
                shape = RoundedCornerShape(28.dp),
                color = PlayerArtworkCard
            ) {
                AsyncImage(
                    model = song.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = song.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = bbh_bartle,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = (34f * typeScale.heading).sp
                ),
                color = WhiteText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Text(
                text = song.artist,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = bbh_bartle,
                    fontSize = (20f * typeScale.title).sp
                ),
                color = PlayerSecondaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerTopActionButton(
                    icon = Icons.Filled.Share,
                    contentDescription = stringResource(id = R.string.cd_player_share)
                )
                PlayerTopActionButton(
                    icon = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.cd_player_like)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                value = progress,
                onValueChange = { progress = it },
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = PlayerTrackDot,
                    activeTrackColor = PlayerTrackDot,
                    inactiveTrackColor = PlayerTrackBackground
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(elapsedSeconds),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = bbh_bartle,
                        fontSize = (16f * typeScale.body).sp
                    ),
                    color = PlayerSecondaryText
                )
                Text(
                    text = formatTime(totalDurationSeconds),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = bbh_bartle,
                        fontSize = (16f * typeScale.body).sp
                    ),
                    color = PlayerSecondaryText
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerTransportButton(
                    icon = Icons.Filled.SkipPrevious,
                    contentDescription = stringResource(id = R.string.cd_player_previous)
                )

                Surface(
                    modifier = Modifier
                        .width(190.dp)
                        .height(74.dp)
                        .clip(RoundedCornerShape(playButtonCorner))
                        .clickable { isPlaying = !isPlaying },
                    shape = RoundedCornerShape(playButtonCorner),
                    color = PlayerButtonSurface
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) {
                                stringResource(id = R.string.cd_player_pause)
                            } else {
                                stringResource(id = R.string.cd_player_play)
                            },
                            tint = PlayerIconOnLight,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isPlaying) {
                                stringResource(id = R.string.player_pause)
                            } else {
                                stringResource(id = R.string.player_play)
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = bbh_bartle,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = (21f * typeScale.title).sp
                            ),
                            color = PlayerIconOnLight
                        )
                    }
                }

                PlayerTransportButton(
                    icon = Icons.Filled.SkipNext,
                    contentDescription = stringResource(id = R.string.cd_player_next)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerOptionButton(
                    icon = Icons.Filled.QueueMusic,
                    contentDescription = stringResource(id = R.string.cd_player_queue)
                )
                PlayerOptionButton(
                    icon = Icons.Filled.FormatAlignLeft,
                    contentDescription = stringResource(id = R.string.cd_player_lyrics)
                )
                PlayerOptionButton(
                    icon = Icons.Filled.Repeat,
                    contentDescription = stringResource(id = R.string.cd_player_repeat)
                )
            }
        }
    }
}

@Composable
private fun PlayerTopActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = { },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = PlayerControlSurface,
            contentColor = WhiteText
        )
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Composable
private fun PlayerTransportButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String
) {
    Surface(
        modifier = Modifier.size(62.dp),
        shape = CircleShape,
        color = PlayerControlSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = WhiteText,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun PlayerOptionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = { },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = PlayerControlSurface,
            contentColor = PlayerSecondaryText
        )
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

private fun formatTime(totalSeconds: Int): String {
    val safeSeconds = totalSeconds.coerceAtLeast(0)
    val minutes = safeSeconds / 60
    val seconds = safeSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
