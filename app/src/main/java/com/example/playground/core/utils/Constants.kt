package com.example.playground.core.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p"

    const val MEDIA_POSTER_WIDTH = 180
    const val MEDIA_POSTER_HEIGHT = 270

    const val DATABASE_NAME = "playground.db"

    object Tables {
        const val WATCHLIST = "watchlist"
    }

    val THEME_KEY = stringPreferencesKey("theme")

}