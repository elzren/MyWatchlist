package com.example.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.playground.core.presentation.composables.BottomNavBar
import com.example.playground.core.presentation.navigation.NavActionManager
import com.example.playground.core.presentation.navigation.Navigation
import com.example.playground.core.presentation.theme.PlaygroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaygroundTheme {
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
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}