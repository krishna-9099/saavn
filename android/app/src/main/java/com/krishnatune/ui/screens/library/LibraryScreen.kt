package com.krishnatune.ui.screens.library

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material.icons.outlined.ViewModule
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.krishnatune.R
import com.krishnatune.ui.screens.home.components.TopBar
import com.krishnatune.ui.theme.LibraryCardSurface
import com.krishnatune.ui.theme.LibraryChipBackground
import com.krishnatune.ui.theme.LibraryChipSelectedBackground
import com.krishnatune.ui.theme.LibraryScreenBackground
import com.krishnatune.ui.theme.LibrarySubtleText
import com.krishnatune.ui.theme.WhiteText

private enum class LibrarySortOption(@StringRes val labelRes: Int) {
    DATE_ADDED(R.string.library_sort_date_added),
    NAME(R.string.library_sort_name),
    DATE_UPDATED(R.string.library_sort_date_updated)
}

private data class LibraryShortcut(
    @StringRes val titleRes: Int,
    val icon: ImageVector
)

@Composable
fun LibraryScreen(onSettingsClick: () -> Unit = {}) {
    var selectedChipIndex by rememberSaveable { mutableStateOf(0) }
    var selectedSortOption by rememberSaveable { mutableStateOf(LibrarySortOption.DATE_ADDED) }
    var sortMenuExpanded by remember { mutableStateOf(false) }
    var isGridView by rememberSaveable { mutableStateOf(true) }

    val categories = listOf(
        R.string.library_chip_playlists,
        R.string.library_chip_songs,
        R.string.library_chip_albums,
        R.string.library_chip_artists
    )
    val shortcuts = listOf(
        LibraryShortcut(R.string.library_item_liked, Icons.Filled.FavoriteBorder),
        LibraryShortcut(R.string.library_item_downloaded, Icons.Filled.Download),
        LibraryShortcut(R.string.library_item_my_top_50, Icons.Filled.TrendingUp),
        LibraryShortcut(R.string.library_item_cached, Icons.Filled.Sync),
        LibraryShortcut(R.string.library_item_uploaded, Icons.Filled.CloudUpload)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LibraryScreenBackground)
    ) {
        TopBar(
            title = stringResource(R.string.library_title),
            onSettingsClick = onSettingsClick
        )

        LibraryCategoryChips(
            categories = categories,
            selectedIndex = selectedChipIndex,
            onSelected = { selectedChipIndex = it }
        )

        LibraryFilterRow(
            selectedSortOption = selectedSortOption,
            sortMenuExpanded = sortMenuExpanded,
            onSortMenuExpandedChange = { sortMenuExpanded = it },
            onSortOptionSelected = { selectedSortOption = it },
            isGridView = isGridView,
            onToggleView = { isGridView = !isGridView }
        )

        if (isGridView) {
            LibraryGridContent(
                items = shortcuts,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LibraryListContent(
                items = shortcuts,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LibraryCategoryChips(
    categories: List<Int>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(categories) { index, labelRes ->
            FilterChip(
                selected = selectedIndex == index,
                onClick = { onSelected(index) },
                label = {
                    Text(
                        text = stringResource(labelRes),
                        fontStyle = FontStyle.Italic
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = LibraryChipBackground,
                    selectedContainerColor = LibraryChipSelectedBackground,
                    labelColor = LibrarySubtleText,
                    selectedLabelColor = WhiteText
                ),
                border = null
            )
        }
    }
}

@Composable
private fun LibraryFilterRow(
    selectedSortOption: LibrarySortOption,
    sortMenuExpanded: Boolean,
    onSortMenuExpandedChange: (Boolean) -> Unit,
    onSortOptionSelected: (LibrarySortOption) -> Unit,
    isGridView: Boolean,
    onToggleView: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSortMenuExpandedChange(true) }
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(selectedSortOption.labelRes),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    ),
                    color = LibrarySubtleText
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.cd_library_sort),
                    tint = LibrarySubtleText
                )
            }

            DropdownMenu(
                expanded = sortMenuExpanded,
                onDismissRequest = { onSortMenuExpandedChange(false) }
            ) {
                LibrarySortOption.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(option.labelRes)) },
                        onClick = {
                            onSortOptionSelected(option)
                            onSortMenuExpandedChange(false)
                        }
                    )
                }
            }
        }

        IconButton(onClick = onToggleView) {
            Icon(
                imageVector = if (isGridView) Icons.Outlined.ViewList else Icons.Outlined.ViewModule,
                contentDescription = if (isGridView) {
                    stringResource(R.string.cd_library_switch_list)
                } else {
                    stringResource(R.string.cd_library_switch_grid)
                },
                tint = WhiteText
            )
        }
    }
}

@Composable
private fun LibraryGridContent(
    items: List<LibraryShortcut>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 120.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        gridItems(items) { item ->
            LibraryGridItem(item = item)
        }
    }
}

@Composable
private fun LibraryGridItem(item: LibraryShortcut) {
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(8.dp),
            color = LibraryCardSurface
        ) {
            Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = WhiteText,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(item.titleRes),
            style = MaterialTheme.typography.titleMedium.copy(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            ),
            color = WhiteText
        )
    }
}

@Composable
private fun LibraryListContent(
    items: List<LibraryShortcut>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = LibraryCardSurface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = WhiteText,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = stringResource(item.titleRes),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = WhiteText
                    )
                }
            }
        }
    }
}
