package com.example.playground.mediaDetail.presentation.movie

import com.example.playground.mediaDetail.domain.model.MovieDetail

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
