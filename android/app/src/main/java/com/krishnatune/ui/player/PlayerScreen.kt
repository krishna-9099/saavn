package com.krishnatune.ui.player

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishnatune.R
import com.krishnatune.models.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val PlayerBackground = Color(0xFF17180F)
private val PlayerMutedText = Color(0xFFD8D2C2)
private val PlayerPrimaryText = Color(0xFFF9F5EA)
private val PlayerOutline = Color(0x66B9B09A)
private val PlayerDarkButton = Color(0xFF212216)
private val PlayerWhiteButton = Color(0xFFF6F4EC)
private val PlayerDarkIcon = Color(0xFF15150F)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerScreen(song: Song, onClose: () -> Unit = {}) {
    val totalDurationSeconds = 185
    var progress by rememberSaveable(song.id) { mutableStateOf(165f / totalDurationSeconds) }
    var screenHeight by remember { mutableStateOf(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(song.id) {
        while (isActive) {
            delay(1000)
            progress = (progress + 1f / totalDurationSeconds).coerceAtMost(1f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerBackground)
            .onGloballyPositioned { screenHeight = it.size.height.toFloat() }
            .graphicsLayer { translationY = offsetY.value }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (offsetY.value > screenHeight * 0.18f) {
                            coroutineScope.launch {
                                offsetY.animateTo(screenHeight, animationSpec = tween(240))
                                onClose()
                            }
                        } else {
                            coroutineScope.launch {
                                offsetY.animateTo(0f, animationSpec = tween(240))
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 26.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.player_now_playing),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = PlayerPrimaryText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.player_playlist_name),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                ),
                color = PlayerMutedText
            )

            Spacer(modifier = Modifier.height(34.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                shape = RoundedCornerShape(12.dp),
                color = Color(0x10FFFFFF)
            ) {
                AsyncImage(
                    model = song.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.title,
                        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 29.sp,
                            lineHeight = 34.sp
                        ),
                        color = PlayerPrimaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp
                        ),
                        color = PlayerPrimaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                PlayerSquareButton(
                    icon = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.cd_player_share)
                )
                Spacer(modifier = Modifier.width(10.dp))
                PlayerSquareButton(
                    icon = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.cd_player_like)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            PlayerProgressBar(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime((progress * totalDurationSeconds).roundToInt().coerceIn(0, totalDurationSeconds)),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = PlayerPrimaryText
                )
                Text(
                    text = formatTime(totalDurationSeconds),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    color = PlayerPrimaryText
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerCircleTransportButton(
                    icon = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.cd_player_previous),
                    modifier = Modifier.size(88.dp)
                )

                Surface(
                    modifier = Modifier
                        .width(200.dp)
                        .height(84.dp)
                        .clip(RoundedCornerShape(42.dp))
                        .clickable { },
                    color = PlayerWhiteButton,
                    shape = RoundedCornerShape(42.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Pause,
                            contentDescription = stringResource(R.string.cd_player_pause),
                            tint = PlayerDarkIcon,
                            modifier = Modifier.size(34.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.player_pause),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic,
                                fontSize = 22.sp
                            ),
                            color = PlayerDarkIcon
                        )
                    }
                }

                PlayerCircleTransportButton(
                    icon = Icons.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.cd_player_next),
                    modifier = Modifier.size(88.dp)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PlayerOutlineToolButton(
                        icon = Icons.Filled.QueueMusic,
                        contentDescription = stringResource(R.string.cd_player_queue)
                    )
                    PlayerOutlineToolButton(
                        icon = Icons.Filled.NightlightRound,
                        contentDescription = stringResource(R.string.cd_player_sleep)
                    )
                    PlayerOutlineToolButton(
                        icon = Icons.Filled.OpenInFull,
                        contentDescription = stringResource(R.string.cd_player_expand)
                    )
                    PlayerOutlineToolButton(
                        icon = Icons.Filled.FormatAlignLeft,
                        contentDescription = stringResource(R.string.cd_player_lyrics)
                    )
                    PlayerOutlineToolButton(
                        icon = Icons.Filled.Repeat,
                        contentDescription = stringResource(R.string.cd_player_repeat)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Surface(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable { },
                    shape = CircleShape,
                    color = PlayerWhiteButton
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.cd_player_more),
                            tint = PlayerDarkIcon,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerSquareButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String
) {
    Surface(
        modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { },
        color = PlayerWhiteButton,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = PlayerDarkIcon,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
private fun PlayerCircleTransportButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable { },
        shape = CircleShape,
        color = PlayerDarkButton
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = PlayerPrimaryText,
                modifier = Modifier.size(38.dp)
            )
        }
    }
}

@Composable
private fun PlayerOutlineToolButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String
) {
    Surface(
        modifier = Modifier
            .size(width = 40.dp, height = 56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { },
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, PlayerOutline, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = PlayerMutedText,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun PlayerProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    BoxWithConstraints(
        modifier = modifier.height(34.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val thumbZoneWidth = 58.dp
        val trackWidth = maxWidth - thumbZoneWidth
        val thumbOffset = calculateThumbOffset(trackWidth, clampedProgress)

        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(18.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(Color.White.copy(alpha = 0.98f))
        ) {
            Box(
                modifier = Modifier
                    .width((trackWidth * clampedProgress).coerceAtLeast(18.dp))
                    .fillMaxSize()
                    .background(Color.White)
            )
        }

        Row(
            modifier = Modifier
                .padding(start = thumbOffset)
                .width(58.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(width = 38.dp, height = 28.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PlayerDarkButton),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(PlayerMutedText)
                )
            }
        }
    }
}

private fun calculateThumbOffset(trackWidth: Dp, progress: Float): Dp {
    val knobPadding = 18.dp
    return (trackWidth - knobPadding) * progress.coerceIn(0f, 1f)
}

private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
