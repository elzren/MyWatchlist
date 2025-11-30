package com.elzren.mywatchlist.home.data.repository

import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(private val tmdbApi: TmdbApiService) : HomeRepository {
    override suspend fun getTrendingMoviesToday(): Flow<DataResult<List<Movie>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val moviesList = tmdbApi.getTrendingMoviesToday()
                emit(DataResult.Success(moviesList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getTrendingShowsToday(): Flow<DataResult<List<Show>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val showsList = tmdbApi.getTrendingShowsToday()
                emit(DataResult.Success(showsList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getPopularMovies(): Flow<DataResult<List<Movie>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val moviesList = tmdbApi.getPopularMovies()
                emit(DataResult.Success(moviesList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getPopularShows(): Flow<DataResult<List<Show>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val showsList = tmdbApi.getPopularShows()
                emit(DataResult.Success(showsList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }
}