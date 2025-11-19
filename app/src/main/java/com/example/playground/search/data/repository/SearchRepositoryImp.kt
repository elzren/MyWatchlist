package com.example.playground.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.core.domain.model.Media
import com.example.playground.search.data.paging.MediaSearchPagingSource
import com.example.playground.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(private val tmdbApi: TmdbApiService) :
    SearchRepository {
    override fun getMediaSearchResults(query: String): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MediaSearchPagingSource(tmdbApi, query) }
        ).flow
    }
}