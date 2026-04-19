package com.krishnatune.ui.utils

import androidx.compose.runtime.Composable
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier

@Composable
fun NetworkImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
    )
}