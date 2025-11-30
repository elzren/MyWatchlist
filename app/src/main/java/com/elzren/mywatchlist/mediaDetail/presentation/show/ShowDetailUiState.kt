package com.elzren.mywatchlist.mediaDetail.presentation.show

import androidx.annotation.StringRes
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail

data class ShowDetailUiState(
    val showDetail: ShowDetail? = null,
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
    val isInWatchlist: Boolean = false
)
