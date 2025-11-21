package com.example.playground.mediaDetail.presentation.movie

import androidx.annotation.StringRes
import com.example.playground.mediaDetail.domain.model.MovieDetail

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
    val isInWatchlist: Boolean = false
)
