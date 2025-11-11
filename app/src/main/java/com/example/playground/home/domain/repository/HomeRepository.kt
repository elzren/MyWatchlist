package com.example.playground.home.domain.repository

import com.example.playground.core.utils.DataResult
import com.example.playground.home.domain.model.Movie
import com.example.playground.home.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTrendingMoviesToday(): Flow<DataResult<List<Movie>>>
    suspend fun getTrendingShowsToday(): Flow<DataResult<List<Show>>>

    suspend fun getPopularMovies(): Flow<DataResult<List<Movie>>>
    suspend fun getPopularShows(): Flow<DataResult<List<Show>>>
}