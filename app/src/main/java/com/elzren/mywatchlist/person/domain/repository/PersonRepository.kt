package com.elzren.mywatchlist.person.domain.repository

import androidx.paging.PagingData
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.person.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun getPersonDetail(personId: Int): Flow<DataResult<Person>>
    suspend fun getPersonMovies(personId: Int): Flow<DataResult<List<Movie>>>
    suspend fun getPersonShows(personId: Int): Flow<DataResult<List<Show>>>
}