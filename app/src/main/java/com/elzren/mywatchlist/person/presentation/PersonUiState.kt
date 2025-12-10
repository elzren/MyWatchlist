package com.elzren.mywatchlist.person.presentation

import androidx.annotation.StringRes
import com.elzren.mywatchlist.home.domain.model.Movie
import com.elzren.mywatchlist.home.domain.model.Show
import com.elzren.mywatchlist.person.domain.model.Person

data class PersonUiState (
    val personId: Int? = null,
    val personDetail: Person? = null,
    val isPersonDetailLoading: Boolean = false,
    @param:StringRes val detailErrorMessage: Int? = null,

    val personMovies: List<Movie> = emptyList(),
    val isPersonMoviesLoading: Boolean = false,
    @param:StringRes val moviesErrorMessage: Int? = null,

    val personShows: List<Show> = emptyList(),
    val isPersonShowsLoading: Boolean = false,
    @param:StringRes val showsErrorMessage: Int? = null,
)