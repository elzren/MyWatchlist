package com.elzren.mywatchlist.core.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p"

    const val MEDIA_POSTER_WIDTH = 180
    const val MEDIA_POSTER_HEIGHT = 270

    const val DATABASE_NAME = "my_watchlist.db"

    object Tables {
        const val WATCHLIST = "watchlist"
    }

    val THEME_KEY = stringPreferencesKey("theme")

    const val GITHUB_REPO_URL = "https://github.com/elzren"

}