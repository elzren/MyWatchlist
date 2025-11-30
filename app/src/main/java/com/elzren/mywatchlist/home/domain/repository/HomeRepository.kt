package com.elzren.mywatchlist.home.domain.repository

import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTrendingMoviesToday(): Flow<DataResult<List<Movie>>>
    suspend fun getTrendingShowsToday(): Flow<DataResult<List<Show>>>

    suspend fun getPopularMovies(): Flow<DataResult<List<Movie>>>
    suspend fun getPopularShows(): Flow<DataResult<List<Show>>>
}