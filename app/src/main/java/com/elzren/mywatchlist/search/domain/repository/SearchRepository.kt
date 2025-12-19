package com.elzren.mywatchlist.search.domain.repository

import androidx.paging.PagingData
import com.elzren.mywatchlist.core.domain.model.MediaModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getMediaSearchResults(query: String): Flow<PagingData<MediaModel>>
}