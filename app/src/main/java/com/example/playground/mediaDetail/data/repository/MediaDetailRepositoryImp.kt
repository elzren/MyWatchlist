package com.example.playground.mediaDetail.data.repository

import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.core.utils.DataResult
import com.example.playground.mediaDetail.domain.model.MovieDetail
import com.example.playground.mediaDetail.domain.model.ShowDetail
import com.example.playground.mediaDetail.domain.repository.MediaDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaDetailRepositoryImp @Inject constructor(private val tmdbApi: TmdbApiService) : MediaDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): Flow<DataResult<MovieDetail>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val detail = tmdbApi.getMovieDetail(movieId)
                emit(DataResult.Success(detail))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getShowDetail(showId: Int): Flow<DataResult<ShowDetail>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val detail = tmdbApi.getShowDetail(showId)
                emit(DataResult.Success(detail))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }
}