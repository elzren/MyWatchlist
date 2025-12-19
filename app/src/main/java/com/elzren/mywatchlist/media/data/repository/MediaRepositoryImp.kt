package com.elzren.mywatchlist.media.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.domain.repository.PreferencesRepository
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.media.data.paging.MoviePagingSource
import com.elzren.mywatchlist.media.data.paging.ShowPagingSource
import com.elzren.mywatchlist.media.domain.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImp @Inject constructor(
    private val tmdbApi: TmdbApiService,
    private val preferencesRepository: PreferencesRepository
) :
    MediaRepository {
    override suspend fun getMovies(
        genres: String?,
        keywords: String?
    ): Flow<PagingData<Movie>> {
        val showNsfw = preferencesRepository.getNsfwFirst()
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    tmdbApi = tmdbApi,
                    showNsfw = showNsfw,
                    genres = genres,
                    keywords = keywords
                )
            }
        ).flow
    }

    override suspend fun getShows(
        genres: String?,
        keywords: String?
    ): Flow<PagingData<Show>> {
        val showNsfw = preferencesRepository.getNsfwFirst()
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                ShowPagingSource(
                    tmdbApi = tmdbApi,
                    showNsfw = showNsfw,
                    genres = genres,
                    keywords = keywords
                )
            }
        ).flow
    }
}