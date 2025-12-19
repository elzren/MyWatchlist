package com.elzren.mywatchlist.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.domain.model.MediaModel
import com.elzren.mywatchlist.core.domain.repository.PreferencesRepository
import com.elzren.mywatchlist.search.data.paging.MediaSearchPagingSource
import com.elzren.mywatchlist.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(
    private val tmdbApi: TmdbApiService,
    private val preferencesRepository: PreferencesRepository
) :
    SearchRepository {
    override suspend fun getMediaSearchResults(query: String): Flow<PagingData<MediaModel>> {
        val showNsfw = preferencesRepository.getNsfwFirst()
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MediaSearchPagingSource(tmdbApi, query, showNsfw) }
        ).flow
    }
}