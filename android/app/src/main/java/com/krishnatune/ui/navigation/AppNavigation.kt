package com.krishnatune.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krishnatune.domain.model.Song
import com.krishnatune.ui.screens.HomeScreen
import com.krishnatune.ui.screens.PlayerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val mockSong = Song("1", "Song One", "Artist", "", "")

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen()
        }

        composable("player") {
            PlayerScreen(song = mockSong)
        }
    }
}
