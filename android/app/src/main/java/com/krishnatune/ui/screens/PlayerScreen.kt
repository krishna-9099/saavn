package com.krishnatune.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krishnatune.domain.model.Song

@Composable
fun PlayerScreen(song: Song) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(250.dp)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {}
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(song.title, style = MaterialTheme.typography.headlineSmall)
        Text(song.artist, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Slider(value = 0.3f, onValueChange = {})

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {}) {
                Text("⏮")
            }
            IconButton(onClick = {}) {
                Text("▶")
            }
            IconButton(onClick = {}) {
                Text("⏭")
            }
        }
    }
}