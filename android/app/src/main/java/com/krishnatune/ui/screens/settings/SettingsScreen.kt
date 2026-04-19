package com.krishnatune.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        SettingsHeader()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                SettingsCategory(
                    title = "ACCOUNT",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.Person,
                            title = "Sign in to sync your data",
                            subtitle = "Sign in with phone/email",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        )
                    )
                )
            }

            item {
                SettingsCategory(
                    title = "PLAYBACK",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.MusicNote,
                            title = "Music Quality",
                            subtitle = "Normal",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.Equalizer,
                            title = "Equalizer",
                            subtitle = "Custom",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.SkipNext,
                            title = "Auto Play Next",
                            type = SettingsItemType.Switch(
                                checked = true,
                                onCheckedChange = { }
                            )
                        ),
                        SettingsItem(
                            icon = Icons.Default.Share,
                            title = "Social Recommendations",
                            type = SettingsItemType.Switch(
                                checked = false,
                                onCheckedChange = { }
                            )
                        )
                    )
                )
            }

            item {
                SettingsCategory(
                    title = "STORAGE",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.Download,
                            title = "Download Quality",
                            subtitle = "High",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.Storage,
                            title = "Storage Used",
                            subtitle = "1.2 GB",
                            type = SettingsItemType.Info,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.DeleteSweep,
                            title = "Clear Cache",
                            type = SettingsItemType.Action(
                                label = "Clear"
                            ),
                            onClick = { }
                        )
                    )
                )
            }

            item {
                SettingsCategory(
                    title = "APPEARANCE",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.DarkMode,
                            title = "Dark Mode",
                            type = SettingsItemType.Switch(
                                checked = true,
                                onCheckedChange = { }
                            )
                        ),
                        SettingsItem(
                            icon = Icons.Default.Palette,
                            title = "Theme Color",
                            subtitle = "Green",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        )
                    )
                )
            }

            item {
                SettingsCategory(
                    title = "ABOUT",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.Info,
                            title = "App Version",
                            subtitle = "1.0.0",
                            type = SettingsItemType.Info,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.Description,
                            title = "Licenses",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.Feedback,
                            title = "Send Feedback",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        ),
                        SettingsItem(
                            icon = Icons.Default.SystemUpdate,
                            title = "Check for Updates",
                            type = SettingsItemType.Navigation,
                            onClick = { }
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun SettingsHeader() {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
private fun SettingsCategory(
    title: String,
    items: List<SettingsItem>
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    SettingsItemRow(item = item)
                    if (index < items.size - 1) {
                        Divider(
                            modifier = Modifier.padding(start = 56.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsItemRow(item: SettingsItem) {
    val rowModifier = if (item.type is SettingsItemType.Navigation || item.type is SettingsItemType.Action) {
        Modifier.clickable { item.onClick() }
    } else {
        Modifier
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(rowModifier)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (item.subtitle != null) {
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        when (val itemType = item.type) {
            is SettingsItemType.Switch -> {
                Switch(
                    checked = itemType.checked,
                    onCheckedChange = itemType.onCheckedChange
                )
            }
            is SettingsItemType.Navigation -> {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            is SettingsItemType.Action -> {
                Text(
                    text = itemType.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is SettingsItemType.Info -> {
                // Info type shows subtitle on the right, no additional UI needed
            }
        }
    }
}

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val type: SettingsItemType,
    val onClick: () -> Unit = {}
)

sealed class SettingsItemType {
    data class Switch(
        val checked: Boolean,
        val onCheckedChange: (Boolean) -> Unit
    ) : SettingsItemType()

    data class Action(
        val label: String
    ) : SettingsItemType()

    data object Navigation : SettingsItemType()
    data object Info : SettingsItemType()
}