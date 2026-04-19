package com.krishnatune.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.krishnatune.models.Song
import com.krishnatune.ui.component.MiniPlayer
import com.krishnatune.ui.screens.home.HomeScreen
import com.krishnatune.ui.player.PlayerScreen
import com.krishnatune.ui.screens.search.SearchScreen
import com.krishnatune.ui.screens.library.LibraryScreen
import com.krishnatune.ui.screens.settings.SettingsScreen
import com.krishnatune.ui.screens.playlist.PlaylistScreen
import com.krishnatune.ui.screens.artist.ArtistScreen
import com.krishnatune.ui.screens.album.AlbumScreen
import com.krishnatune.ui.screens.download.DownloadScreen
import com.krishnatune.ui.screens.podcast.PodcastScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Placeholder current song for the MiniPlayer
    val currentSong = Song("1", "Blinding Lights", "The Weeknd", "https://picsum.photos/200", "")

    val items = listOf(
        NavigationItem("home", "Home", Icons.Filled.Home),
        NavigationItem("search", "Search", Icons.Filled.Search),
        NavigationItem("library", "Library", Icons.Outlined.LibraryMusic)
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Hide bottom bar on full player screen
            if (currentRoute != "player") {
                Column {
                    MiniPlayer(song = currentSong, onClick = {
                        navController.navigate("player")
                    })
                    NavigationBar {
                        items.forEach { item ->
                            NavigationBarItem(
                                icon = { Icon(item.icon, contentDescription = item.title) },
                                label = { Text(item.title) },
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(onSongClick = {
                        navController.navigate("player")
                    }, onSettingsClick = {
                        navController.navigate("settings")
                    })
                }
                composable("search") {
                    SearchScreen()
                }
                composable("library") {
                    LibraryScreen(onSettingsClick = {
                        navController.navigate("settings")
                    })
                }
                composable("settings") {
                    SettingsScreen()
                }
                composable("player") {
                    PlayerScreen(
                        song = currentSong,
                        onClose = { navController.popBackStack() }
                    )
                }
                composable("playlist/{id}") { backStackEntry ->
                    PlaylistScreen()
                }
                composable("artist/{id}") { backStackEntry ->
                    ArtistScreen()
                }
                composable("album/{id}") { backStackEntry ->
                    AlbumScreen()
                }
                composable("download") {
                    DownloadScreen()
                }
                composable("podcast/{id}") { backStackEntry ->
                    PodcastScreen()
                }
            }
        }
    }
}

data class NavigationItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
