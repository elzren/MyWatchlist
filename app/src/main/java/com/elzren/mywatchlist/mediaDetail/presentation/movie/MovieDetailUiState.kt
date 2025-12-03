package com.elzren.mywatchlist.mediaDetail.presentation.movie

import androidx.annotation.StringRes
import com.elzren.mywatchlist.core.domain.model.Media
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Cast

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
    val isInWatchlist: Boolean = false,

    val movieCast: List<Cast> = emptyList(),
    val isCastLoading: Boolean = false,

    val movieRecommendations: List<Media> = emptyList(),
    val isRecommendationsLoading: Boolean = false
)
