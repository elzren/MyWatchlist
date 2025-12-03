package com.elzren.mywatchlist.mediaDetail.data.repository

import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Cast
import com.elzren.mywatchlist.mediaDetail.domain.repository.MediaDetailRepository
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

    override suspend fun getMovieCast(movieId: Int): Flow<DataResult<List<Cast>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val credit = tmdbApi.getMovieCredit(movieId)
                emit(DataResult.Success(credit.cast))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getShowCast(showId: Int): Flow<DataResult<List<Cast>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val credit = tmdbApi.getShowCredit(showId)
                emit(DataResult.Success(credit.cast))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }


}