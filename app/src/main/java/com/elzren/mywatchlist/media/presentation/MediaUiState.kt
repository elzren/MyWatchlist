package com.elzren.mywatchlist.media.presentation

import androidx.paging.PagingData
import com.elzren.mywatchlist.core.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MediaUiState(
    val movies: Flow<PagingData<Media>> = emptyFlow(),
    val shows: Flow<PagingData<Media>> = emptyFlow(),
    val genres: String? = null,
    val keywords: String? = null
)