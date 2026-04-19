package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.krishnatune.R
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.ui.utils.NetworkImage
import kotlin.math.max

@Composable
fun SpeedDialSection(
    title: String,
    items: List<HomeSectionItem>,
    pinnedIds: Set<String>,
    onItemClick: (HomeSectionItem) -> Unit,
    onItemLongClick: (HomeSectionItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (items.isEmpty()) return

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 10.dp),
        )

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val minCardWidth = 116.dp
            val spacing = 10.dp
            val columns = max(2, ((maxWidth + spacing) / (minCardWidth + spacing)).toInt())
            val rows = items.chunked(columns)

            Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
                rows.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing),
                    ) {
                        rowItems.forEach { item ->
                            SpeedDialCard(
                                item = item,
                                isPinned = item.speedDialId() in pinnedIds,
                                onClick = { onItemClick(item) },
                                onLongClick = { onItemLongClick(item) },
                                modifier = Modifier.weight(1f),
                            )
                        }

                        repeat(columns - rowItems.size) {
                            Spacer(
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SpeedDialCard(
    item: HomeSectionItem,
    isPinned: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .aspectRatio(1f)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NetworkImage(
                url = item.image ?: "",
                modifier = Modifier.fillMaxSize(),
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            brush =
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color.Black.copy(alpha = 0.18f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.76f),
                                        ),
                                ),
                        ),
            )

            if (isPinned) {
                Box(
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.78f))
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = androidx.compose.ui.res.stringResource(R.string.cd_speed_dial_pinned),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp),
                    )
                }
            }

            Text(
                text = item.title ?: "Unknown",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomStart).padding(10.dp),
            )
        }
    }
}

private fun HomeSectionItem.speedDialId(): String {
    return id ?: perma_url.orEmpty()
}
