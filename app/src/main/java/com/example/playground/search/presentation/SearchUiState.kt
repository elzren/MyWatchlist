package com.example.playground.search.presentation

import androidx.paging.PagingData
import com.example.playground.core.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val query: String = "",
    val searchResults: Flow<PagingData<Media>> = emptyFlow()
)
