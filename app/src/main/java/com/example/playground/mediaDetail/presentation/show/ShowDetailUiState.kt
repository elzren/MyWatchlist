package com.example.playground.mediaDetail.presentation.show

import com.example.playground.mediaDetail.domain.model.ShowDetail

data class ShowDetailUiState(
    val showDetail: ShowDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
