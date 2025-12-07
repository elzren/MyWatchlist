package com.elzren.mywatchlist.media.domain

import androidx.paging.PagingData
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getMovies(genres: String?, keywords: String?): Flow<PagingData<Movie>>
    fun getShows(genres: String?, keywords: String?): Flow<PagingData<Show>>
}