package com.example.playground.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.playground.BuildConfig.TMDB_API_KEY
import com.example.playground.core.domain.model.MediaResponse
import com.example.playground.core.domain.model.Movie
import com.example.playground.core.domain.model.Show

interface TmdbApiService {

    @GET("trending/movie/day")
    suspend fun getTrendingMoviesToday(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): MediaResponse<Movie>

    @GET("trending/tv/day")
    suspend fun getTrendingShowsToday(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): MediaResponse<Show>
}