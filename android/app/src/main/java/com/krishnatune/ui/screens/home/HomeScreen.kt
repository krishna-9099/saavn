package com.krishnatune.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.krishnatune.R
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.ui.screens.home.components.ArtistStationsSection
import com.krishnatune.ui.screens.home.components.HomeItemActionsSheet
import com.krishnatune.ui.screens.home.components.NewReleasesSection
import com.krishnatune.ui.screens.home.components.RadioStationsSection
import com.krishnatune.ui.screens.home.components.SectionRow
import com.krishnatune.ui.screens.home.components.SpeedDialSection
import com.krishnatune.ui.screens.home.components.TopBar
import com.krishnatune.viewmodels.HomeUiState
import com.krishnatune.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onItemClick: (HomeSectionItem) -> Unit,
    onSettingsClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val speedDialItems by viewModel.speedDialItems.collectAsState()
    val pinnedIds by viewModel.pinnedIds.collectAsState()

    var activeActionItem by remember { mutableStateOf<HomeSectionItem?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        if (speedDialItems.isNotEmpty()) {
                            item(key = "speed-dial-section") {
                                SpeedDialSection(
                                    title = stringResource(R.string.speed_dial_title),
                                    items = speedDialItems,
                                    pinnedIds = pinnedIds,
                                    onItemClick = onItemClick,
                                    onItemLongClick = { activeActionItem = it },
                                    onAddClick = { /* TODO: Show add speed dial dialog */ },
                                )
                            }
                        }

                        items(state.modules.size) { index ->
                            val module = state.modules[index]
                            val sectionItems = module.initialItems

                            if (!sectionItems.isNullOrEmpty()) {
                                when {
                                    module.config.source == "radio" -> {
                                        RadioStationsSection(
                                            title = module.config.title
                                                ?.takeIf { it.isNotBlank() }
                                                ?: stringResource(R.string.radio_stations_title),
                                            items = sectionItems,
                                            onItemClick = onItemClick,
                                            onItemLongClick = { activeActionItem = it },
                                        )
                                    }
                                    module.config.source == "new_albums" -> {
                                        NewReleasesSection(
                                            title = module.config.title
                                                ?.takeIf { it.isNotBlank() }
                                                ?: "New Releases",
                                            items = module.pagedItems,
                                            onItemClick = onItemClick,
                                            onItemLongClick = { activeActionItem = it },
                                        )
                                    }
                                    module.config.source == "artist_recos" -> {
                                        ArtistStationsSection(
                                            title = module.config.title
                                                ?.takeIf { it.isNotBlank() }
                                                ?: "Artist Stations",
                                            items = sectionItems,
                                            onItemClick = onItemClick,
                                            onItemLongClick = { activeActionItem = it },
                                        )
                                    }
                                    module.config.type == "list" -> {
                                        VerticalListSection(
                                            title = module.config.title ?: module.key,
                                            items = sectionItems,
                                            onItemClick = onItemClick,
                                            onItemLongClick = { activeActionItem = it },
                                        )
                                    }
                                    else -> {
                                        SectionRow(
                                            title = module.config.title ?: module.key,
                                            items = module.pagedItems,
                                            onItemClick = onItemClick,
                                            onItemLongClick = { activeActionItem = it },
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
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    androidx.compose.material.icons.Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    activeActionItem?.let { item ->
        HomeItemActionsSheet(
            item = item,
            isPinned = item.speedDialIdentity() in pinnedIds,
            onDismiss = { activeActionItem = null },
            onTogglePin = { selected ->
                viewModel.toggleSpeedDial(selected)
                activeActionItem = null
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalListSection(
    title: String,
    items: List<HomeSectionItem>,
    onItemClick: (HomeSectionItem) -> Unit,
    onItemLongClick: ((HomeSectionItem) -> Unit)? = null,
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
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.tertiary,
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
                    .combinedClickable(
                        onClick = { onItemClick(item) },
                        onLongClick = { onItemLongClick?.invoke(item) },
                    ),
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
                IconButton(onClick = { onItemLongClick?.invoke(item) }) {
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

private fun HomeSectionItem.speedDialIdentity(): String {
    return id ?: perma_url.orEmpty()
}
