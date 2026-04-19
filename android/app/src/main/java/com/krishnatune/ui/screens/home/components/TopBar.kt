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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.krishnatune.R
import com.krishnatune.ui.theme.AdaptiveTypeScale
import com.krishnatune.ui.theme.bbh_bartle
import com.krishnatune.ui.theme.LibraryCardSurface
import com.krishnatune.ui.theme.LibraryScreenBackground
import com.krishnatune.ui.theme.ProfilePopupSwitchTrack
import com.krishnatune.ui.theme.rememberAdaptiveTypeScale
import com.krishnatune.ui.theme.WhiteText
import kotlin.math.min

@Composable
fun TopBar(
    title: String,
    onSettingsClick: () -> Unit = {}
) {
    var isProfilePopupVisible by rememberSaveable { mutableStateOf(false) }
    val typeScale = rememberAdaptiveTypeScale()
    val widthDp = LocalConfiguration.current.screenWidthDp
    val homeTitleSize = if (widthDp >= 600) {
        min(34f * typeScale.heading, 22f)
    } else {
        34f * typeScale.heading
    }

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
                fontFamily = bbh_bartle,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = homeTitleSize.sp
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
            },
            typeScale = typeScale,
        )
    }
}

@Composable
private fun ProfilePopup(
    onDismiss: () -> Unit,
    onSettingsClick: () -> Unit,
    typeScale: AdaptiveTypeScale,
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
                            fontFamily = bbh_bartle,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = (27f * typeScale.heading).sp
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
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = bbh_bartle,
                                fontStyle = FontStyle.Italic,
                                fontSize = (24f * typeScale.title).sp
                            )
                        )
                        OutlinedButton(
                            onClick = { },
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = WhiteText)
                        ) {
                            Text(
                                text = stringResource(R.string.profile_popup_logout),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontFamily = bbh_bartle,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = (15f * typeScale.body).sp,
                                )
                            )
                        }
                    }
                }

                ProfileInfoCard(
                    icon = Icons.Outlined.VpnKey,
                    title = stringResource(R.string.profile_popup_tap_show_token),
                    typeScale = typeScale,
                )
                ProfileToggleCard(
                    icon = Icons.Outlined.AddCircle,
                    title = stringResource(R.string.profile_popup_more_content),
                    checked = moreContentEnabled,
                    onCheckedChange = { moreContentEnabled = it },
                    typeScale = typeScale,
                )
                ProfileToggleCard(
                    icon = Icons.Outlined.Sync,
                    title = stringResource(R.string.profile_popup_auto_sync),
                    checked = autoSyncEnabled,
                    onCheckedChange = { autoSyncEnabled = it },
                    typeScale = typeScale,
                )

                Spacer(modifier = Modifier.height(6.dp))

                ProfileMenuItem(
                    icon = Icons.Outlined.Extension,
                    title = stringResource(R.string.profile_popup_integrations),
                    typeScale = typeScale,
                )
                ProfileMenuItem(
                    icon = Icons.Outlined.Settings,
                    title = stringResource(R.string.profile_popup_settings),
                    onClick = onSettingsClick,
                    typeScale = typeScale,
                )
                ProfileMenuItem(
                    icon = Icons.Outlined.Update,
                    title = stringResource(R.string.profile_popup_new_version),
                    subtitle = stringResource(R.string.profile_popup_version),
                    typeScale = typeScale,
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    typeScale: AdaptiveTypeScale,
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
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = bbh_bartle,
                    fontStyle = FontStyle.Italic,
                    fontSize = (19f * typeScale.title).sp,
                )
            )
        }
    }
}

@Composable
private fun ProfileToggleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    typeScale: AdaptiveTypeScale,
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
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = bbh_bartle,
                        fontStyle = FontStyle.Italic,
                        fontSize = (19f * typeScale.title).sp,
                    )
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
    onClick: (() -> Unit)? = null,
    typeScale: AdaptiveTypeScale,
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
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = bbh_bartle,
                    fontStyle = FontStyle.Italic,
                    fontSize = (19f * typeScale.title).sp,
                )
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = bbh_bartle,
                        fontStyle = FontStyle.Italic,
                        fontSize = (14f * typeScale.body).sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
