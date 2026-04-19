package com.krishnatune.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krishnatune.domain.model.Song
import com.krishnatune.ui.components.MiniPlayer

@Composable
fun HomeScreenV3(onSongClick: () -> Unit) {
    val mockSongs = listOf(
        Song("1", "Song One", "Artist", "", ""),
        Song("2", "Song Two", "Artist", "", ""),
        Song("3", "Song Three", "Artist", "", "")
    )

    val currentSong = mockSongs.first()

    Column(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.weight(1f).padding(16.dp)) {
            Text("KrishnaTune", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Trending", style = MaterialTheme.typography.titleMedium)

            LazyRow {
                items(mockSongs) { song ->
                    SongCard(song, onClick = onSongClick)
                }
            }
        }

        MiniPlayer(song = currentSong, onClick = onSongClick)
    }
}

@Composable
fun SongCard(song: Song, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(140.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier
                .height(80.dp)
                .fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            Text(song.title)
            Text(song.artist, style = MaterialTheme.typography.bodySmall)
        }
    }
}