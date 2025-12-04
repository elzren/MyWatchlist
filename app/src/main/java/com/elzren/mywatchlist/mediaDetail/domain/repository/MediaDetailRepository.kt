package com.elzren.mywatchlist.mediaDetail.domain.repository

import com.elzren.mywatchlist.core.domain.model.Media
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Cast
import com.elzren.mywatchlist.mediaDetail.domain.model.keyword.Keyword
import kotlinx.coroutines.flow.Flow

interface MediaDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Flow<DataResult<MovieDetail>>
    suspend fun getShowDetail(showId: Int): Flow<DataResult<ShowDetail>>

    suspend fun getMovieCast(movieId: Int): Flow<DataResult<List<Cast>>>
    suspend fun getShowCast(showId: Int): Flow<DataResult<List<Cast>>>

    suspend fun getMovieRecommendations(movieId: Int): Flow<DataResult<List<Media>>>
    suspend fun getShowRecommendations(showId: Int): Flow<DataResult<List<Media>>>

    suspend fun getMovieKeywords(movieId: Int): Flow<DataResult<List<Keyword>>>
    suspend fun getShowKeywords(showId: Int): Flow<DataResult<List<Keyword>>>
}