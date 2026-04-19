package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.ui.theme.bbh_bartle
import com.krishnatune.ui.theme.rememberAdaptiveTypeScale
import com.krishnatune.ui.utils.NetworkImage
import kotlinx.coroutines.flow.Flow
import kotlin.math.min

@Composable
fun SectionRow(
    title: String,
    items: Flow<PagingData<HomeSectionItem>>,
    onItemClick: (HomeSectionItem) -> Unit,
    onItemLongClick: ((HomeSectionItem) -> Unit)? = null,
) {
    val lazyItems = items.collectAsLazyPagingItems()
    val typeScale = rememberAdaptiveTypeScale()
    val widthDp = LocalConfiguration.current.screenWidthDp
    val sectionTitleSize = if (widthDp >= 600) {
        min(18f * typeScale.title, 15f)
    } else {
        18f * typeScale.title
    }

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = bbh_bartle,
                fontWeight = FontWeight.SemiBold,
                fontSize = sectionTitleSize.sp,
                lineHeight = (sectionTitleSize + 2f).sp
            ),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(
                count = lazyItems.itemCount,
                key = { index -> lazyItems[index]?.id ?: "section-placeholder-$index" }
            ) { index ->
                val item = lazyItems[index]
                if (item != null) {
                    HomeItemCard(
                        item = item,
                        onClick = { onItemClick(item) },
                        onLongClick = onItemLongClick,
                    )
                } else {
                    HomeItemPlaceholder()
                }
            }

            if (lazyItems.loadState.append is LoadState.Loading) {
                item(key = "section-append-loader") {
                    SectionAppendLoader()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeItemCard(
    item: HomeSectionItem,
    onClick: () -> Unit,
    onLongClick: ((HomeSectionItem) -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(140.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { onLongClick?.invoke(item) },
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            NetworkImage(
                url = item.image ?: "",
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title ?: "Unknown",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    lineHeight = 15.sp
                )
            )
            Text(
                text = item.subtitle ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    lineHeight = 13.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HomeItemPlaceholder() {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(140.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.12f))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(14.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.12f))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(12.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.08f))
            )
        }
    }
}

@Composable
private fun SectionAppendLoader() {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(48.dp)
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 2.dp)
    }
}
