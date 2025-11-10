package com.example.playground.settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.playground.core.presentation.navigation.NavActionManager

@Composable
fun SettingsScreen(navActionManager: NavActionManager, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Column {
            Text(text = "Settings screen")
            Button(onClick = navActionManager::goBack) { Text(text = "Go Back") }
        }
    }
}