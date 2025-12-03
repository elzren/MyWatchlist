package com.elzren.mywatchlist.core.data.api

import com.elzren.mywatchlist.core.domain.model.MediaModel
import retrofit2.http.GET
import retrofit2.http.Query
import com.elzren.mywatchlist.core.domain.model.MediaResponse
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Credit
import retrofit2.http.Path

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

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int
    ): MovieDetail

    @GET("tv/{showId}")
    suspend fun getShowDetail(
        @Path("showId") showId: Int
    ): ShowDetail

    @GET("search/multi")
    suspend fun getMediaSearchResults(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MediaResponse<MediaModel>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredit(
        @Path("movieId") movieId: Int
    ): Credit

    @GET("tv/{showId}/credits")
    suspend fun getShowCredit(
        @Path("showId") showId: Int
    ): Credit

    @GET("movie/{movieId}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1
    ): MediaResponse<MediaModel>

    @GET("tv/{showId}/recommendations")
    suspend fun getShowRecommendations(
        @Path("showId") showId: Int,
        @Query("page") page: Int = 1
    ): MediaResponse<MediaModel>
}