package com.krishnatune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.krishnatune.ui.navigation.AppNavigation
import com.krishnatune.ui.theme.KrishnaTuneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KrishnaTuneTheme(darkTheme = true) {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}
