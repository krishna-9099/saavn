package com.krishnatune

import androidx.compose.runtime.Composable
import com.krishnatune.ui.navigation.AppNavigation
import com.krishnatune.ui.theme.KrishnaTuneTheme

@Composable
fun AppEntry() {
    KrishnaTuneTheme(darkTheme = true) {
        AppNavigation()
    }
}
