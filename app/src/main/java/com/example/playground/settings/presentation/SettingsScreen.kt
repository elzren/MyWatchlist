package com.example.playground.settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.playground.core.domain.model.Theme
import com.example.playground.core.presentation.navigation.NavActionManager

@Composable
fun SettingsScreen(navActionManager: NavActionManager, modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Column {
            Text(text = "Settings screen")
            Button(onClick = { viewModel.setTheme(Theme.SYSTEM) }) { Text(text = "System theme") }
            Button(onClick = { viewModel.setTheme(Theme.LIGHT) }) { Text(text = "Light theme") }
            Button(onClick = { viewModel.setTheme(Theme.DARK) }) { Text(text = "Dark theme") }
        }
    }
}