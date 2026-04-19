package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishnatune.R
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.ui.theme.RadioCardGradientGlowGreen
import com.krishnatune.ui.theme.RadioCardGradientGlowPurple
import com.krishnatune.ui.theme.RadioCardImageOverlay
import com.krishnatune.ui.theme.RadioCardPlayContainer
import com.krishnatune.ui.theme.RadioLabelText
import com.krishnatune.ui.theme.RadioSectionBackgroundTop

@Composable
fun RadioStationsSection(
    title: String,
    items: List<HomeSectionItem>,
    onItemClick: (HomeSectionItem) -> Unit,
) {
    if (items.isEmpty()) return

    val widthDp = LocalConfiguration.current.screenWidthDp
    val visibleCount = when {
        widthDp >= 840 -> 6
        widthDp >= 600 -> 5
        else -> 4
    }
    val radioItems = items.take(visibleCount)

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
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = radioItems,
                key = { item -> item.id ?: "${item.title}-${item.image}" }
            ) { item ->
                RadioStationCard(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun RadioStationCard(
    item: HomeSectionItem,
    onClick: () -> Unit,
) {
    val stationName = item.title?.takeIf { it.isNotBlank() }
        ?: stringResource(R.string.radio_station_unknown)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(108.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                RadioCardGradientGlowGreen.copy(alpha = 0.65f),
                                RadioCardGradientGlowPurple.copy(alpha = 0.4f),
                            )
                        )
                    )
                    .blur(14.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(12.dp, CircleShape, clip = false)
                    .clip(CircleShape)
                    .background(RadioSectionBackgroundTop)
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
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                ) {
                    AsyncImage(
                        model = item.image,
                        contentDescription = stringResource(
                            R.string.cd_radio_station_image,
                            stationName,
                        ),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(RadioCardImageOverlay)
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(RadioCardPlayContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = stringResource(
                                R.string.cd_play_radio_station,
                                stationName,
                            ),
                            tint = RadioLabelText,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stationName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                lineHeight = 16.sp,
            ),
            color = RadioLabelText,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
