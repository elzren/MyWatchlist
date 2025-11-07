package com.example.playground.home.domain.repository

import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.core.domain.model.Movie
import com.example.playground.core.domain.model.Show
import com.example.playground.core.utils.DataResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTrendingMoviesToday(): Flow<DataResult<List<Movie>>>
    suspend fun getTrendingShowsToday(): Flow<DataResult<List<Show>>>
}