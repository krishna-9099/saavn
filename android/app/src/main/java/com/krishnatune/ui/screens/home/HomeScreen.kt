package com.krishnatune.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krishnatune.domain.model.Song
import com.krishnatune.ui.screens.home.components.SectionRow
import com.krishnatune.ui.screens.home.components.TopBar

@Composable
fun HomeScreen(
    onSongClick: () -> Unit
) {

    val mockSongs = List(10) {
        Song(
            it.toString(),
            "Song $it",
            "Artist",
            "https://picsum.photos/200?random=$it",
            ""
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TopBar()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionRow(title = "Trending", songs = mockSongs, onSongClick)
        }

        item {
            SectionRow(title = "For You", songs = mockSongs, onSongClick)
        }

        item {
            SectionRow(title = "Recently Played", songs = mockSongs, onSongClick)
        }
    }
}
