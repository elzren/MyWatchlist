package com.example.playground.mediaDetail.presentation.show

import androidx.annotation.StringRes
import com.example.playground.mediaDetail.domain.model.ShowDetail

data class ShowDetailUiState(
    val showDetail: ShowDetail? = null,
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null
)
