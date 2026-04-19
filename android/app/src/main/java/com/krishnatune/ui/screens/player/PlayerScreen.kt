package com.krishnatune.ui.screens.player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import coil.compose.AsyncImage
import com.krishnatune.R
import com.krishnatune.domain.model.Song
import com.krishnatune.ui.theme.PlayerArtworkCard
import com.krishnatune.ui.theme.PlayerArtworkPlate
import com.krishnatune.ui.theme.PlayerButtonSurface
import com.krishnatune.ui.theme.PlayerControlSurface
import com.krishnatune.ui.theme.PlayerGradientBottom
import com.krishnatune.ui.theme.PlayerGradientTop
import com.krishnatune.ui.theme.PlayerIconOnLight
import com.krishnatune.ui.theme.PlayerMenuDivider
import com.krishnatune.ui.theme.PlayerMenuHandle
import com.krishnatune.ui.theme.PlayerMenuPrimaryItemSurface
import com.krishnatune.ui.theme.PlayerMenuQuickActionSurface
import com.krishnatune.ui.theme.PlayerMenuScrim
import com.krishnatune.ui.theme.PlayerMenuSheetBackground
import com.krishnatune.ui.theme.PlayerMenuSubtitle
import com.krishnatune.ui.theme.PlayerMenuVolumeBackground
import com.krishnatune.ui.theme.PlayerMenuVolumeIcon
import com.krishnatune.ui.theme.PlayerMenuVolumeLevel
import com.krishnatune.ui.theme.PlayerMenuVolumeTrack
import com.krishnatune.ui.theme.PlayerSecondaryText
import com.krishnatune.ui.theme.PlayerTrackBackground
import com.krishnatune.ui.theme.PlayerTrackDot
import com.krishnatune.ui.theme.WhiteText
import kotlin.math.roundToInt

private enum class PlayerMoreSheetStage {
    HALF,
    FULL
}

@Composable
fun PlayerScreen(song: Song) {
    val totalDurationSeconds = 196
    var progress by remember { mutableStateOf(4f / totalDurationSeconds) }
    var showMoreMenu by remember { mutableStateOf(false) }
    var moreSheetStage by remember { mutableStateOf(PlayerMoreSheetStage.HALF) }
    progress = progress.coerceIn(0f, 1f)

    val elapsedSeconds = (progress * totalDurationSeconds)
        .roundToInt()
        .coerceIn(0, totalDurationSeconds)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PlayerGradientTop, PlayerGradientBottom)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.player_now_playing),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Cursive,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                ),
                color = WhiteText
            )

            Text(
                text = stringResource(id = R.string.player_playlist_name),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Cursive,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                color = PlayerSecondaryText
            )

            Spacer(modifier = Modifier.height(34.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(338.dp),
                shape = RoundedCornerShape(16.dp),
                color = PlayerArtworkCard
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 50.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp),
                        color = PlayerArtworkPlate
                    ) {
                        AsyncImage(
                            model = song.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontFamily = FontFamily.Cursive,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp
                        ),
                        color = WhiteText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Cursive,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        ),
                        color = PlayerSecondaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PlayerSquareActionButton(
                        icon = Icons.Filled.Reply,
                        contentDescription = stringResource(id = R.string.cd_player_share)
                    )
                    PlayerSquareActionButton(
                        icon = Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.cd_player_like)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            PlayerProgress(
                progress = progress,
                elapsedSeconds = elapsedSeconds,
                totalSeconds = totalDurationSeconds
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PlayerRoundControlButton(
                    icon = Icons.Filled.SkipPrevious,
                    contentDescription = stringResource(id = R.string.cd_player_previous)
                )

                Surface(
                    modifier = Modifier
                        .height(84.dp)
                        .widthIn(min = 210.dp),
                    shape = RoundedCornerShape(42.dp),
                    color = PlayerButtonSurface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Pause,
                            contentDescription = stringResource(id = R.string.cd_player_pause),
                            tint = PlayerIconOnLight,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(id = R.string.player_pause),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Cursive,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            ),
                            color = PlayerIconOnLight
                        )
                    }
                }

                PlayerRoundControlButton(
                    icon = Icons.Filled.SkipNext,
                    contentDescription = stringResource(id = R.string.cd_player_next)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PlayerSmallOptionButton(
                        icon = Icons.Filled.QueueMusic,
                        contentDescription = stringResource(id = R.string.cd_player_queue)
                    )
                    PlayerSmallOptionButton(
                        icon = Icons.Filled.Bedtime,
                        contentDescription = stringResource(id = R.string.cd_player_sleep)
                    )
                    PlayerSmallOptionButton(
                        icon = Icons.Filled.OpenInFull,
                        contentDescription = stringResource(id = R.string.cd_player_expand)
                    )
                    PlayerSmallOptionButton(
                        icon = Icons.Filled.FormatAlignLeft,
                        contentDescription = stringResource(id = R.string.cd_player_lyrics)
                    )
                    PlayerSmallOptionButton(
                        icon = Icons.Filled.Repeat,
                        contentDescription = stringResource(id = R.string.cd_player_repeat)
                    )
                }

                PlayerMoreButton(
                    contentDescription = stringResource(id = R.string.cd_player_more),
                    onClick = {
                        moreSheetStage = PlayerMoreSheetStage.HALF
                        showMoreMenu = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (showMoreMenu) {
            PlayerMoreMenuSheet(
                sheetStage = moreSheetStage,
                onExpandFull = { moreSheetStage = PlayerMoreSheetStage.FULL },
                onDismiss = {
                    showMoreMenu = false
                    moreSheetStage = PlayerMoreSheetStage.HALF
                }
            )
        }
    }
}

@Composable
private fun PlayerMoreButton(contentDescription: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(62.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        shape = CircleShape,
        color = PlayerButtonSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = contentDescription,
                tint = PlayerIconOnLight,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun PlayerMoreMenuSheet(
    sheetStage: PlayerMoreSheetStage,
    onExpandFull: () -> Unit,
    onDismiss: () -> Unit
) {
    val dismissTapSource = remember { MutableInteractionSource() }
    val sheetTapSource = remember { MutableInteractionSource() }
    val targetHeightFraction = if (sheetStage == PlayerMoreSheetStage.HALF) 0.56f else 0.9f
    val animatedHeightFraction by animateFloatAsState(
        targetValue = targetHeightFraction,
        label = "player_more_sheet_height"
    )
    val expandOnUpScrollConnection = remember(sheetStage) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (sheetStage == PlayerMoreSheetStage.HALF && available.y < -2f) {
                    onExpandFull()
                    return available
                }
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerMenuScrim)
            .clickable(
                interactionSource = dismissTapSource,
                indication = null,
                onClick = onDismiss
            )
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(animatedHeightFraction)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .nestedScroll(expandOnUpScrollConnection)
                .clickable(
                    interactionSource = sheetTapSource,
                    indication = null,
                    onClick = {}
                ),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = PlayerMenuSheetBackground
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 6.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(70.dp)
                        .height(5.dp)
                        .clip(CircleShape)
                        .background(PlayerMenuHandle)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PlayerVolumeBlock()

                    Divider(color = PlayerMenuDivider, thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PlayerMenuQuickAction(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.Radio,
                            title = stringResource(id = R.string.player_menu_start_radio),
                            contentDescription = stringResource(id = R.string.cd_player_menu_start_radio)
                        )
                        PlayerMenuQuickAction(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.PlaylistAdd,
                            title = stringResource(id = R.string.player_menu_add_to_playlist),
                            contentDescription = stringResource(id = R.string.cd_player_menu_add_to_playlist)
                        )
                        PlayerMenuQuickAction(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.Link,
                            title = stringResource(id = R.string.player_menu_copy_link),
                            contentDescription = stringResource(id = R.string.cd_player_menu_copy_link)
                        )
                    }

                    PlayerMenuMainItem(
                        icon = Icons.Filled.LibraryAdd,
                        title = stringResource(id = R.string.player_menu_add_to_library),
                        contentDescription = stringResource(id = R.string.cd_player_menu_add_to_library)
                    )
                    PlayerMenuMainItem(
                        icon = Icons.Filled.Download,
                        title = stringResource(id = R.string.player_menu_download),
                        contentDescription = stringResource(id = R.string.cd_player_menu_download)
                    )
                    PlayerMenuMainItem(
                        icon = Icons.Filled.Group,
                        title = stringResource(id = R.string.player_menu_listen_together),
                        contentDescription = stringResource(id = R.string.cd_player_menu_listen_together)
                    )
                    PlayerMenuMainItem(
                        icon = Icons.Filled.Info,
                        title = stringResource(id = R.string.player_menu_details),
                        subtitle = stringResource(id = R.string.player_menu_details_subtitle),
                        contentDescription = stringResource(id = R.string.cd_player_menu_details)
                    )
                    PlayerMenuMainItem(
                        icon = Icons.Filled.Equalizer,
                        title = stringResource(id = R.string.player_menu_equalizer),
                        subtitle = stringResource(id = R.string.player_menu_equalizer_subtitle),
                        contentDescription = stringResource(id = R.string.cd_player_menu_equalizer)
                    )
                    PlayerMenuMainItem(
                        icon = Icons.Filled.Tune,
                        title = stringResource(id = R.string.player_menu_advanced),
                        subtitle = stringResource(id = R.string.player_menu_advanced_subtitle),
                        contentDescription = stringResource(id = R.string.cd_player_menu_advanced)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun PlayerVolumeBlock() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(16.dp),
        color = PlayerMenuVolumeBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.VolumeUp,
                contentDescription = stringResource(id = R.string.cd_player_volume),
                tint = PlayerMenuVolumeIcon,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp)
                    .clip(CircleShape)
                    .background(PlayerMenuVolumeTrack)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(PlayerMenuVolumeLevel)
            )
        }
    }
}

@Composable
private fun PlayerMenuQuickAction(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    contentDescription: String
) {
    Surface(
        modifier = modifier.height(116.dp),
        shape = RoundedCornerShape(22.dp),
        color = PlayerMenuQuickActionSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = WhiteText,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Cursive,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                color = PlayerSecondaryText,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PlayerMenuMainItem(
    icon: ImageVector,
    title: String,
    contentDescription: String,
    subtitle: String? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        color = PlayerMenuPrimaryItemSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = WhiteText,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Cursive,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    color = WhiteText
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Cursive,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        ),
                        color = PlayerMenuSubtitle
                    )
                }
            }
        }
    }
}

@Composable
private fun PlayerSquareActionButton(icon: ImageVector, contentDescription: String) {
    Surface(
        modifier = Modifier
            .width(58.dp)
            .height(54.dp),
        shape = RoundedCornerShape(14.dp),
        color = PlayerButtonSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = PlayerIconOnLight,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun PlayerRoundControlButton(icon: ImageVector, contentDescription: String) {
    Surface(
        modifier = Modifier.size(82.dp),
        shape = CircleShape,
        color = PlayerControlSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = WhiteText,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
private fun PlayerSmallOptionButton(icon: ImageVector, contentDescription: String) {
    Surface(
        modifier = Modifier.size(52.dp),
        shape = RoundedCornerShape(10.dp),
        color = PlayerControlSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = PlayerSecondaryText,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun PlayerProgress(progress: Float, elapsedSeconds: Int, totalSeconds: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(78.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(PlayerTrackDot)
            )
            Spacer(modifier = Modifier.width(12.dp))
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .height(24.dp)
            ) {
                val thumbSize = 10.dp
                val thumbOffset = (maxWidth - thumbSize) * progress.coerceIn(0f, 1f)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .height(18.dp)
                        .clip(CircleShape)
                        .background(PlayerTrackBackground)
                )

                Box(
                    modifier = Modifier
                        .offset(x = thumbOffset)
                        .size(thumbSize)
                        .clip(CircleShape)
                        .background(PlayerTrackDot)
                        .align(Alignment.CenterStart)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(elapsedSeconds),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.SemiBold
                ),
                color = WhiteText
            )
            Text(
                text = formatTime(totalSeconds),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.SemiBold
                ),
                color = WhiteText
            )
        }
    }
}

private fun formatTime(totalSeconds: Int): String {
    val safeSeconds = totalSeconds.coerceAtLeast(0)
    val minutes = safeSeconds / 60
    val seconds = safeSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
