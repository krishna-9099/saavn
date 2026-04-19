package com.krishnatune.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.krishnatune.R
import com.krishnatune.domain.model.HomeSectionItem
import com.krishnatune.ui.screens.home.components.SectionRow
import com.krishnatune.ui.screens.home.components.TopBar

@Composable
fun HomeScreen(
    onSongClick: () -> Unit,
    onSettingsClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = stringResource(R.string.home_title),
                onSettingsClick = onSettingsClick
            )

            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomeUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                    }
                }
                is HomeUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 100.dp) // padding for mini player
                    ) {
                        val data = state.data
                        
                        items(state.sortedModules.size) { index ->
                            val module = state.sortedModules[index]
                            val sectionItems = getItemsForSource(module.second.source, data)

                            if (!sectionItems.isNullOrEmpty()) {
                                when (module.second.type) {
                                    "list" -> {
                                        VerticalListSection(
                                            title = module.second.title ?: module.first,
                                            items = sectionItems,
                                            onItemClick = { onSongClick() }
                                        )
                                    }
                                    else -> {
                                        SectionRow(
                                            title = module.second.title ?: module.first,
                                            items = sectionItems,
                                            onItemClick = { onSongClick() }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Actions on the right
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(androidx.compose.material.icons.Icons.Default.Mic, contentDescription = "Voice Search")
            }
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF006064).copy(alpha = 0.9f), // Teal/Dark blue from image
                modifier = Modifier.size(56.dp)
            ) {
                Icon(androidx.compose.material.icons.Icons.Default.Shuffle, contentDescription = "Shuffle", tint = Color.White)
            }
        }
    }
}

@Composable
fun VerticalListSection(
    title: String,
    items: List<HomeSectionItem>,
    onItemClick: (HomeSectionItem) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (title.contains("Trending", ignoreCase = true)) "Quick picks" else title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4FC3F7) // Light blue from image
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedButton(
                onClick = { /* TODO */ },
                shape = CircleShape,
                modifier = Modifier.height(32.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text("Play all", style = MaterialTheme.typography.bodySmall)
            }
        }

        items.take(4).forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onItemClick(item) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.krishnatune.ui.utils.NetworkImage(
                    url = item.image ?: "",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title ?: "Unknown",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = item.subtitle ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

private fun getItemsForSource(source: String?, data: com.krishnatune.domain.model.HomeDataResponse): List<HomeSectionItem>? {
    return when (source) {
        "new_trending" -> data.new_trending
        "top_playlists" -> data.top_playlists
        "new_albums" -> data.new_albums
        "browse_discover" -> data.browse_discover
        "charts" -> data.charts
        "radio" -> data.radio
        "artist_recos" -> data.artist_recos
        // "promo:vx:data:xx" dynamic sources are currently omitted for simplicity
        else -> null
    }
}
