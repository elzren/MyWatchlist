package com.elzren.mywatchlist.media.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.media.data.paging.MoviePagingSource
import com.elzren.mywatchlist.media.data.paging.ShowPagingSource
import com.elzren.mywatchlist.media.domain.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImp @Inject constructor(private val tmdbApi: TmdbApiService) :
    MediaRepository {
    override fun getMovies(
        genres: String?,
        keywords: String?
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(tmdbApi, genres, keywords) }
        ).flow
    }

    override fun getShows(
        genres: String?,
        keywords: String?
    ): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ShowPagingSource(tmdbApi, genres, keywords) }
        ).flow
    }
}