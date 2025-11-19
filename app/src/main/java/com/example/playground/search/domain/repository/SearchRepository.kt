package com.example.playground.search.domain.repository

import androidx.paging.PagingData
import com.example.playground.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getMediaSearchResults(query: String): Flow<PagingData<Media>>
}