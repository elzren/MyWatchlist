package com.elzren.mywatchlist.person.domain.model

import androidx.annotation.StringRes
import com.elzren.mywatchlist.R

enum class PersonTab(@param:StringRes val string: Int) {
    OVERVIEW(R.string.overview),
    MOVIES(R.string.movies),
    SHOWS(R.string.shows)
}