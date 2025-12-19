package com.elzren.mywatchlist.core.data.api

import com.elzren.mywatchlist.core.domain.model.MediaModel
import retrofit2.http.GET
import retrofit2.http.Query
import com.elzren.mywatchlist.core.domain.model.MediaResponse
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.VideosResponse
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Credit
import com.elzren.mywatchlist.mediaDetail.domain.model.keyword.MovieKeywordResponse
import com.elzren.mywatchlist.mediaDetail.domain.model.keyword.ShowKeywordResponse
import com.elzren.mywatchlist.person.domain.model.Credits
import com.elzren.mywatchlist.person.domain.model.Person
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
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false
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

    @GET("movie/{movieId}/keywords")
    suspend fun getMovieKeywords(
        @Path("movieId") movieId: Int,
    ): MovieKeywordResponse

    @GET("tv/{showId}/keywords")
    suspend fun getShowKeywords(
        @Path("showId") showId: Int,
    ): ShowKeywordResponse

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int,
    ): VideosResponse

    @GET("tv/{showId}/videos")
    suspend fun getShowVideos(
        @Path("showId") showId: Int,
    ): VideosResponse

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("with_genres") withGenres: String?,
        @Query("with_keywords") withKeywords: String?,
        @Query("without_keywords") withoutKeywords: String?,
        @Query("include_adult") includeAdult: Boolean = false
    ): MediaResponse<Movie>

    @GET("discover/tv")
    suspend fun getShows(
        @Query("page") page: Int = 1,
        @Query("with_genres") withGenres: String?,
        @Query("with_keywords") withKeywords: String?,
        @Query("without_keywords") withoutKeywords: String?,
        @Query("include_adult") includeAdult: Boolean = false
    ): MediaResponse<Show>

    @GET("person/{personId}")
    suspend fun getPersonDetail(
        @Path("personId") personId: Int
    ): Person

    @GET("person/{personId}/movie_credits")
    suspend fun getPersonMovies(
        @Path("personId") personId: Int
    ): Credits<Movie>

    @GET("person/{personId}/tv_credits")
    suspend fun getPersonShows(
        @Path("personId") personId: Int
    ): Credits<Show>

    @GET("person/{personId}/combined_credits")
    suspend fun getPersonCombinedMedia(
        @Path("personId") personId: Int
    ): Credits<MediaModel>
}