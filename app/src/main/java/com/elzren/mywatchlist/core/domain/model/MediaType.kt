package com.elzren.mywatchlist.core.domain.model

import androidx.annotation.StringRes
import com.elzren.mywatchlist.R

enum class MediaType(@param:StringRes val string: Int) {
    MOVIE(R.string.movies),
    SHOW(R.string.shows)
}