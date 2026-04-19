package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.ui.theme.rememberAdaptiveTypeScale
import com.krishnatune.ui.utils.NetworkImage
import kotlin.math.min

@Composable
fun SectionRow(
    title: String,
    items: List<HomeSectionItem>,
    onItemClick: (HomeSectionItem) -> Unit
) {
    val typeScale = rememberAdaptiveTypeScale()
    val widthDp = LocalConfiguration.current.screenWidthDp
    val sectionTitleSize = if (widthDp >= 600) {
        min(22f * typeScale.title, 16f)
    } else {
        22f * typeScale.title
    }

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = sectionTitleSize.sp
            ),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(items) { item ->
                HomeItemCard(item = item, onClick = { onItemClick(item) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeItemCard(item: HomeSectionItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(140.dp)
            .clickable { onClick() },
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = item.subtitle ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
