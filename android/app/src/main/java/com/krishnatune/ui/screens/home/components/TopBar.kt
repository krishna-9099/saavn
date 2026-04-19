package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.krishnatune.R
import com.krishnatune.ui.theme.LibraryCardSurface
import com.krishnatune.ui.theme.LibraryScreenBackground
import com.krishnatune.ui.theme.ProfilePopupSwitchTrack
import com.krishnatune.ui.theme.WhiteText

@Composable
fun TopBar(
    title: String,
    onSettingsClick: () -> Unit = {}
) {
    var isProfilePopupVisible by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Outlined.History, contentDescription = stringResource(R.string.cd_history))
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Outlined.TrendingUp, contentDescription = stringResource(R.string.cd_trending))
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Outlined.Group, contentDescription = stringResource(R.string.cd_groups))
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Profile image (pink flower placeholder)
            Image(
                painter = rememberAsyncImagePainter("https://picsum.photos/seed/flower/200"),
                contentDescription = stringResource(R.string.cd_profile),
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { isProfilePopupVisible = true },
                contentScale = ContentScale.Crop
            )
        }
    }

    if (isProfilePopupVisible) {
        ProfilePopup(
            onDismiss = { isProfilePopupVisible = false },
            onSettingsClick = {
                isProfilePopupVisible = false
                onSettingsClick()
            }
        )
    }
}

@Composable
private fun ProfilePopup(
    onDismiss: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var moreContentEnabled by rememberSaveable { mutableStateOf(true) }
    var autoSyncEnabled by rememberSaveable { mutableStateOf(true) }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = LibraryCardSurface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.profile_popup_title),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.cd_profile_popup_close)
                        )
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = LibraryScreenBackground,
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter("https://picsum.photos/seed/flower/200"),
                            contentDescription = stringResource(R.string.cd_profile),
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.profile_popup_user_name),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
                        )
                        OutlinedButton(
                            onClick = { },
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = WhiteText)
                        ) {
                            Text(
                                text = stringResource(R.string.profile_popup_logout),
                                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic)
                            )
                        }
                    }
                }

                ProfileInfoCard(
                    icon = Icons.Outlined.VpnKey,
                    title = stringResource(R.string.profile_popup_tap_show_token)
                )
                ProfileToggleCard(
                    icon = Icons.Outlined.AddCircle,
                    title = stringResource(R.string.profile_popup_more_content),
                    checked = moreContentEnabled,
                    onCheckedChange = { moreContentEnabled = it }
                )
                ProfileToggleCard(
                    icon = Icons.Outlined.Sync,
                    title = stringResource(R.string.profile_popup_auto_sync),
                    checked = autoSyncEnabled,
                    onCheckedChange = { autoSyncEnabled = it }
                )

                Spacer(modifier = Modifier.height(6.dp))

                ProfileMenuItem(
                    icon = Icons.Outlined.Extension,
                    title = stringResource(R.string.profile_popup_integrations)
                )
                ProfileMenuItem(
                    icon = Icons.Outlined.Settings,
                    title = stringResource(R.string.profile_popup_settings),
                    onClick = onSettingsClick
                )
                ProfileMenuItem(
                    icon = Icons.Outlined.Update,
                    title = stringResource(R.string.profile_popup_new_version),
                    subtitle = stringResource(R.string.profile_popup_version)
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = LibraryScreenBackground,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
            )
        }
    }
}

@Composable
private fun ProfileToggleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = LibraryScreenBackground,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                thumbContent = {
                    if (checked) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            tint = LibraryCardSurface
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = WhiteText,
                    checkedTrackColor = ProfilePopupSwitchTrack,
                    uncheckedThumbColor = WhiteText,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
