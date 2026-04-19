package com.krishnatune.ui.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krishnatune.domain.model.Song
import com.krishnatune.ui.components.SongCardV2

@Composable
fun SectionRow(
    title: String,
    songs: List<Song>,
    onSongClick: () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow {
            items(songs) { song ->
                SongCardV2(song = song, onClick = onSongClick)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
