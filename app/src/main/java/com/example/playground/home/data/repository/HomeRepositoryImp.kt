package com.example.playground.home.data.repository

import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.home.domain.model.Movie
import com.example.playground.home.domain.model.Show
import com.example.playground.core.utils.DataResult
import com.example.playground.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(private val api: TmdbApiService) : HomeRepository {
    override suspend fun getTrendingMoviesToday(): Flow<DataResult<List<Movie>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val moviesList = api.getTrendingMoviesToday()
                emit(DataResult.Success(moviesList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    override suspend fun getTrendingShowsToday(): Flow<DataResult<List<Show>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val showsList = api.getTrendingShowsToday()
                emit(DataResult.Success(showsList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    override suspend fun getPopularMovies(): Flow<DataResult<List<Movie>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val moviesList = api.getPopularMovies()
                emit(DataResult.Success(moviesList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    override suspend fun getPopularShows(): Flow<DataResult<List<Show>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val showsList = api.getPopularShows()
                emit(DataResult.Success(showsList.results))
            } catch (e: Exception) {
                emit(DataResult.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }
}