package com.example.playground.home.presentation

import com.example.playground.core.domain.model.Movie
import com.example.playground.core.domain.model.Show

data class HomeUiState(
    val trendingMovies: List<Movie> = emptyList(),
    val trendingShows: List<Show> = emptyList(),
    val isTrendingMoviesLoading: Boolean = false,
    val isTrendingShowsLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isLoading: Boolean
        get() {     // custom getter gets called whenever property is accessed
            return isTrendingMoviesLoading || isTrendingShowsLoading
        }
}
