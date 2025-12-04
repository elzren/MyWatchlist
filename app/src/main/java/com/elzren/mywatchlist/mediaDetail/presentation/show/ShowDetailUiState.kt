package com.elzren.mywatchlist.mediaDetail.presentation.show

import androidx.annotation.StringRes
import com.elzren.mywatchlist.core.domain.model.Media
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Cast
import com.elzren.mywatchlist.mediaDetail.domain.model.keyword.Keyword

data class ShowDetailUiState(
    val showDetail: ShowDetail? = null,
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
    val isInWatchlist: Boolean = false,

    val showCast: List<Cast> = emptyList(),
    val isCastLoading: Boolean = false,

    val showRecommendations: List<Media> = emptyList(),
    val isRecommendationsLoading: Boolean = false,

    val showKeywords: List<Keyword> = emptyList(),
    val isKeywordsLoading: Boolean = false
)
