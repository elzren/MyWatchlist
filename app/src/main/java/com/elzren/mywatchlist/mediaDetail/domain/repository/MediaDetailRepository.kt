package com.elzren.mywatchlist.mediaDetail.domain.repository

import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import kotlinx.coroutines.flow.Flow

interface MediaDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Flow<DataResult<MovieDetail>>
    suspend fun getShowDetail(showId: Int): Flow<DataResult<ShowDetail>>
}