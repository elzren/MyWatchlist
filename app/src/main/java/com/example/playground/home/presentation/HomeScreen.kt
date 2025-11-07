package com.example.playground.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val moviesList = homeUiState.trendingMovies

    Column(modifier = modifier) {
        if (moviesList.isNotEmpty()) {
            Text(text = moviesList[0].title)
        }
        if (homeUiState.isLoading) {
            Text(text = "Loading...")
        }
        if (homeUiState.errorMessage != null) {
            Text(text = "Error: " + (homeUiState.errorMessage ?: "Something went wrong"))
        }
    }
}