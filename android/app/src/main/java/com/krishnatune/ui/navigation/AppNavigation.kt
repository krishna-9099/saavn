package com.krishnatune.ui.navigation

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.krishnatune.models.HomeSectionItem
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
import com.krishnatune.viewmodels.HomeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val application = LocalContext.current.applicationContext as Application
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(application))
    val pinnedIds by homeViewModel.pinnedIds.collectAsState()

    // Placeholder current song for the MiniPlayer
    val currentSong = Song("1", "Blinding Lights", "The Weeknd", "https://picsum.photos/200", "")

    val items = listOf(
        NavigationItem("home", "Home", Icons.Filled.Home),
        NavigationItem("search", "Search", Icons.Filled.Search),
        NavigationItem("library", "Library", Icons.Outlined.LibraryMusic)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Hide bottom bar on full player screen
            if (currentRoute != "player") {
                Column {
                    MiniPlayer(song = currentSong, onClick = {
                        navController.navigate("player")
                    })
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ) {
                        items.forEach { item ->
                            NavigationBarItem(
                                icon = { Icon(item.icon, contentDescription = item.title) },
                                label = {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
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
                    HomeScreen(
                        viewModel = homeViewModel,
                        onItemClick = { item ->
                            navController.navigateForHomeItem(item)
                        },
                        onSettingsClick = {
                        navController.navigate("settings")
                        },
                    )
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
                        isSpeedDialPinned = currentSong.id in pinnedIds,
                        onToggleSpeedDial = {
                            homeViewModel.toggleSpeedDial(currentSong.toHomeSectionItem())
                        },
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

private fun NavHostController.navigateForHomeItem(item: HomeSectionItem) {
    val itemType = item.type?.lowercase(Locale.ROOT).orEmpty()
    val itemId = item.id?.takeIf { it.isNotBlank() }

    when (itemType) {
        "album" -> {
            if (itemId != null) navigate("album/$itemId") else navigate("player")
        }

        "artist" -> {
            if (itemId != null) navigate("artist/$itemId") else navigate("player")
        }

        "playlist", "radio", "station", "chart", "mix" -> {
            if (itemId != null) navigate("playlist/$itemId") else navigate("player")
        }

        "show", "podcast" -> {
            if (itemId != null) navigate("podcast/$itemId") else navigate("player")
        }

        else -> navigate("player")
    }
}

private fun Song.toHomeSectionItem(): HomeSectionItem {
    return HomeSectionItem(
        id = id,
        title = title,
        subtitle = artist,
        type = "song",
        image = image,
        perma_url = null,
        language = null,
        more_info = null,
    )
}

data class NavigationItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
