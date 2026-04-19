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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isVeryCompact = maxHeight < 700.dp
            val isCompact = maxHeight < 780.dp
            val horizontalPadding = if (isCompact) 20.dp else 26.dp
            val verticalPadding = if (isCompact) 10.dp else 14.dp
            val artworkWidthFraction = when {
                isVeryCompact -> 0.72f
                isCompact -> 0.82f
                else -> 0.9f
            }

            val nowPlayingSize = if (isCompact) 16.sp else 18.sp
            val playlistSize = if (isCompact) 14.sp else 15.sp
            val songTitleSize = when {
                isVeryCompact -> 24.sp
                isCompact -> 26.sp
                else -> 29.sp
            }
            val songArtistSize = if (isCompact) 16.sp else 18.sp
            val timeTextSize = if (isCompact) 14.sp else 16.sp

            val squareButtonSize = if (isCompact) 60.dp else 72.dp
            val squareButtonIconSize = if (isCompact) 28.dp else 34.dp

            val transportButtonSize = when {
                isVeryCompact -> 70.dp
                isCompact -> 78.dp
                else -> 88.dp
            }
            val transportIconSize = if (isCompact) 32.dp else 38.dp

            val pauseButtonWidth = when {
                isVeryCompact -> 160.dp
                isCompact -> 180.dp
                else -> 200.dp
            }
            val pauseButtonHeight = when {
                isVeryCompact -> 68.dp
                isCompact -> 76.dp
                else -> 84.dp
            }
            val pauseTextSize = if (isCompact) 20.sp else 22.sp
            val pauseIconSize = if (isCompact) 30.dp else 34.dp

            val utilityButtonWidth = if (isCompact) 36.dp else 40.dp
            val utilityButtonHeight = if (isCompact) 50.dp else 56.dp
            val utilityIconSize = if (isCompact) 22.dp else 24.dp
            val overflowButtonSize = if (isCompact) 56.dp else 64.dp
            val overflowIconSize = if (isCompact) 26.dp else 28.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = horizontalPadding, vertical = verticalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.player_now_playing),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = nowPlayingSize
                    ),
                    color = PlayerPrimaryText
                )

                Spacer(modifier = Modifier.height(if (isCompact) 4.dp else 6.dp))

                Text(
                    text = stringResource(R.string.player_playlist_name),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontSize = playlistSize,
                        lineHeight = if (isCompact) 18.sp else 20.sp
                    ),
                    color = PlayerMutedText
                )

                Spacer(
                    modifier = Modifier.height(
                        if (isVeryCompact) 20.dp else if (isCompact) 26.dp else 34.dp
                    )
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth(artworkWidthFraction)
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(if (isCompact) 10.dp else 12.dp),
                    color = Color(0x10FFFFFF)
                ) {
                    AsyncImage(
                        model = song.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        if (isVeryCompact) 16.dp else if (isCompact) 22.dp else 28.dp
                    )
                )

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
                                fontSize = songTitleSize,
                                lineHeight = if (isCompact) 30.sp else 34.sp
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
                                fontSize = songArtistSize
                            ),
                            color = PlayerPrimaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(if (isCompact) 10.dp else 12.dp))

                    PlayerSquareButton(
                        icon = Icons.Filled.Share,
                        contentDescription = stringResource(R.string.cd_player_share),
                        size = squareButtonSize,
                        iconSize = squareButtonIconSize,
                    )
                    Spacer(modifier = Modifier.width(if (isCompact) 8.dp else 10.dp))
                    PlayerSquareButton(
                        icon = Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(R.string.cd_player_like),
                        size = squareButtonSize,
                        iconSize = squareButtonIconSize,
                    )
                }

                Spacer(modifier = Modifier.height(if (isCompact) 20.dp else 26.dp))

                PlayerProgressBar(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(if (isCompact) 6.dp else 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(
                            (progress * totalDurationSeconds)
                                .roundToInt()
                                .coerceIn(0, totalDurationSeconds)
                        ),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontSize = timeTextSize
                        ),
                        color = PlayerPrimaryText
                    )
                    Text(
                        text = formatTime(totalDurationSeconds),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontSize = timeTextSize
                        ),
                        color = PlayerPrimaryText
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        if (isVeryCompact) 18.dp else if (isCompact) 26.dp else 34.dp
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PlayerCircleTransportButton(
                        icon = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.cd_player_previous),
                        modifier = Modifier.size(transportButtonSize),
                        iconSize = transportIconSize,
                    )

                    Surface(
                        modifier = Modifier
                            .width(pauseButtonWidth)
                            .height(pauseButtonHeight)
                            .clip(RoundedCornerShape(pauseButtonHeight / 2))
                            .clickable { },
                        color = PlayerWhiteButton,
                        shape = RoundedCornerShape(pauseButtonHeight / 2)
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
                                modifier = Modifier.size(pauseIconSize)
                            )
                            Spacer(modifier = Modifier.width(if (isCompact) 10.dp else 12.dp))
                            Text(
                                text = stringResource(R.string.player_pause),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = pauseTextSize
                                ),
                                color = PlayerDarkIcon
                            )
                        }
                    }

                    PlayerCircleTransportButton(
                        icon = Icons.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.cd_player_next),
                        modifier = Modifier.size(transportButtonSize),
                        iconSize = transportIconSize,
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        if (isVeryCompact) 18.dp else if (isCompact) 26.dp else 36.dp
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(if (isCompact) 4.dp else 6.dp)
                    ) {
                        PlayerOutlineToolButton(
                            icon = Icons.Filled.QueueMusic,
                            contentDescription = stringResource(R.string.cd_player_queue),
                            buttonWidth = utilityButtonWidth,
                            buttonHeight = utilityButtonHeight,
                            iconSize = utilityIconSize,
                        )
                        PlayerOutlineToolButton(
                            icon = Icons.Filled.NightlightRound,
                            contentDescription = stringResource(R.string.cd_player_sleep),
                            buttonWidth = utilityButtonWidth,
                            buttonHeight = utilityButtonHeight,
                            iconSize = utilityIconSize,
                        )
                        PlayerOutlineToolButton(
                            icon = Icons.Filled.OpenInFull,
                            contentDescription = stringResource(R.string.cd_player_expand),
                            buttonWidth = utilityButtonWidth,
                            buttonHeight = utilityButtonHeight,
                            iconSize = utilityIconSize,
                        )
                        PlayerOutlineToolButton(
                            icon = Icons.Filled.FormatAlignLeft,
                            contentDescription = stringResource(R.string.cd_player_lyrics),
                            buttonWidth = utilityButtonWidth,
                            buttonHeight = utilityButtonHeight,
                            iconSize = utilityIconSize,
                        )
                        PlayerOutlineToolButton(
                            icon = Icons.Filled.Repeat,
                            contentDescription = stringResource(R.string.cd_player_repeat),
                            buttonWidth = utilityButtonWidth,
                            buttonHeight = utilityButtonHeight,
                            iconSize = utilityIconSize,
                        )
                    }

                    Spacer(modifier = Modifier.width(if (isCompact) 10.dp else 14.dp))

                    Surface(
                        modifier = Modifier
                            .size(overflowButtonSize)
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
                                modifier = Modifier.size(overflowIconSize)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(if (isCompact) 10.dp else 14.dp))
            }
        }
    }
}

@Composable
private fun PlayerSquareButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    size: Dp = 72.dp,
    iconSize: Dp = 34.dp,
) {
    Surface(
        modifier = Modifier
            .size(size)
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
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun PlayerCircleTransportButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = 38.dp,
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
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun PlayerOutlineToolButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    buttonWidth: Dp = 40.dp,
    buttonHeight: Dp = 56.dp,
    iconSize: Dp = 24.dp,
) {
    Surface(
        modifier = Modifier
            .size(width = buttonWidth, height = buttonHeight)
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
                modifier = Modifier.size(iconSize)
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
