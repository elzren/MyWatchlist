package com.elzren.mywatchlist.mediaDetail.presentation.movie

import androidx.annotation.StringRes
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
    val isInWatchlist: Boolean = false
)
