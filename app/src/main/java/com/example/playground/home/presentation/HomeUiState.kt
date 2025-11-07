package com.example.playground.home.presentation

import com.example.playground.core.domain.model.Movie
import com.example.playground.core.domain.model.Show

data class HomeUiState(
    val trendingMovies: List<Movie> = emptyList(),
    val trendingShows: List<Show> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
