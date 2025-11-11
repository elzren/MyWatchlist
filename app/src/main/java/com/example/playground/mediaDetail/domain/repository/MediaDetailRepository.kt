package com.example.playground.mediaDetail.domain.repository

import com.example.playground.core.utils.DataResult
import com.example.playground.mediaDetail.domain.model.MovieDetail
import com.example.playground.mediaDetail.domain.model.ShowDetail
import kotlinx.coroutines.flow.Flow

interface MediaDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Flow<DataResult<MovieDetail>>
    suspend fun getShowDetail(showId: Int): Flow<DataResult<ShowDetail>>
}