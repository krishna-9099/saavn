package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.ui.theme.RadioCardGradientGlowGreen
import com.krishnatune.ui.theme.RadioCardGradientGlowPurple
import com.krishnatune.ui.theme.RadioLabelText
import com.krishnatune.ui.theme.RadioSectionBackgroundTop

@Composable
fun ArtistStationsSection(
    title: String,
    items: List<HomeSectionItem>,
    onItemClick: (HomeSectionItem) -> Unit,
    onItemLongClick: ((HomeSectionItem) -> Unit)? = null,
) {
    if (items.isEmpty()) return

    val screenWidth = LocalConfiguration.current.screenWidthDp
    // avatar = ~18% of screen width, capped for tablets
    val avatarSize: Dp = (screenWidth * 0.18f).coerceIn(72f, 120f).dp
    val cardWidth: Dp = (screenWidth * 0.22f).coerceIn(80f, 140f).dp
    val fontSize = ((screenWidth * 0.035f).coerceIn(11f, 15f)).sp

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 24.sp,
            ),
            color = RadioLabelText,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(
                items = items.take(4),
                key = { it.id ?: "${it.title}-${it.image}" }
            ) { item ->
                ArtistAvatarCard(
                    item = item,
                    avatarSize = avatarSize,
                    cardWidth = cardWidth,
                    fontSize = fontSize,
                    onClick = { onItemClick(item) },
                    onLongClick = onItemLongClick,
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ArtistAvatarCard(
    item: HomeSectionItem,
    avatarSize: Dp,
    cardWidth: Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    onClick: () -> Unit,
    onLongClick: ((HomeSectionItem) -> Unit)? = null,
) {
    val name = item.title?.takeIf { it.isNotBlank() } ?: "Artist"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(cardWidth)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { onLongClick?.invoke(item) },
            )
    ) {
        Surface(
            modifier = Modifier
                .size(avatarSize)
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            RadioCardGradientGlowGreen,
                            RadioCardGradientGlowPurple,
                            RadioCardGradientGlowGreen,
                        )
                    ),
                    shape = CircleShape,
                ),
            shape = CircleShape,
            shadowElevation = 8.dp,
            tonalElevation = 0.dp,
            color = RadioSectionBackgroundTop,
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = fontSize,
            ),
            color = RadioLabelText,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}
