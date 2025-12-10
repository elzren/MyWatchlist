package com.elzren.mywatchlist.person.data.repository

import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.person.domain.model.Person
import com.elzren.mywatchlist.person.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PersonRepositoryImp @Inject constructor(private val tmdbApi: TmdbApiService) :
    PersonRepository {
    override suspend fun getPersonDetail(personId: Int): Flow<DataResult<Person>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val personDetail = tmdbApi.getPersonDetail(personId)
                emit(DataResult.Success(personDetail))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getPersonMovies(personId: Int): Flow<DataResult<List<Movie>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val response = tmdbApi.getPersonMovies(personId)
                emit(DataResult.Success(response.cast))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }

    override suspend fun getPersonShows(personId: Int): Flow<DataResult<List<Show>>> {
        return flow {
            emit(DataResult.Loading())
            try {
                val response = tmdbApi.getPersonShows(personId)
                emit(DataResult.Success(response.cast))
            } catch (e: Exception) {
                emit(DataResult.Error(e))
            }
        }
    }


}