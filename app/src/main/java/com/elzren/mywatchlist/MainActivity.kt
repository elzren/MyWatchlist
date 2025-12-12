package com.elzren.mywatchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.elzren.mywatchlist.core.domain.model.Theme
import com.elzren.mywatchlist.core.presentation.composables.BottomNavBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.presentation.navigation.Navigation
import com.elzren.mywatchlist.core.presentation.theme.MyWatchlistTheme
import com.elzren.mywatchlist.core.presentation.theme.dark_scrim
import com.elzren.mywatchlist.core.presentation.theme.light_scrim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val theme by viewModel.theme.collectAsState()
            val isDarkTheme =
                if (theme == Theme.SYSTEM) isSystemInDarkTheme() else theme == Theme.DARK

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { isDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        light_scrim.toArgb(),
                        dark_scrim.toArgb(),
                    ) { isDarkTheme },
                )
                onDispose { }
            }

            MyWatchlistTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val navActionManager = NavActionManager.rememberNavActionManager(navController)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController = navController) },
                    contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                )
                { innerPadding ->
                    Navigation(
                        navController = navController,
                        navActionManager = navActionManager,
                        padding = innerPadding
                    )
                }
            }
        }
    }
}