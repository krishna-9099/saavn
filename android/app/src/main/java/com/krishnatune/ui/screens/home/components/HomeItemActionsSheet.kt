package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.krishnatune.R
import com.krishnatune.models.HomeSectionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeItemActionsSheet(
    item: HomeSectionItem,
    isPinned: Boolean,
    onDismiss: () -> Unit,
    onTogglePin: (HomeSectionItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = if (isPinned) stringResource(R.string.speed_dial_unpin) else stringResource(R.string.speed_dial_pin),
                )
            },
            supportingContent = {
                Text(
                    text = item.title ?: stringResource(R.string.speed_dial_unknown_item),
                )
            },
            leadingContent = {
                Icon(
                    imageVector = if (isPinned) Icons.Filled.Remove else Icons.Filled.Add,
                    contentDescription = null,
                )
            },
            modifier = Modifier.clickable {
                onTogglePin(item)
            },
        )
        ListItem(
            headlineContent = { Text(text = stringResource(R.string.speed_dial_cancel)) },
            modifier = Modifier.clickable(onClick = onDismiss),
        )
    }
}
