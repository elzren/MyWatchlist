package com.example.playground.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.playground.core.domain.model.MediaResponse
import com.example.playground.home.domain.model.Movie
import com.example.playground.home.domain.model.Show

interface TmdbApiService {

    @GET("trending/movie/day")
    suspend fun getTrendingMoviesToday(
        @Query("page") page: Int = 1,
    ): MediaResponse<Movie>

    @GET("trending/tv/day")
    suspend fun getTrendingShowsToday(
        @Query("page") page: Int = 1,
    ): MediaResponse<Show>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): MediaResponse<Movie>

    @GET("tv/popular")
    suspend fun getPopularShows(
        @Query("page") page: Int = 1,
    ): MediaResponse<Show>
}