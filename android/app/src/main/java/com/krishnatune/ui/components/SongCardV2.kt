package com.krishnatune.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krishnatune.domain.model.Song
import com.krishnatune.ui.utils.NetworkImage

@Composable
fun SongCardV2(song: Song, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(160.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            NetworkImage(
                url = song.image,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(song.title)
            Text(song.artist, style = MaterialTheme.typography.bodySmall)
        }
    }
}