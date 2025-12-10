package com.elzren.mywatchlist.core.presentation.navigation

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Home

    @Serializable
    object Search

    @Serializable
    data class MovieDetail(val movieId: Int)

    @Serializable
    data class ShowDetail(val showId: Int)

    @Serializable
    object Watchlist

    @Serializable
    object Settings

    @Serializable
    data class MediaScreen(
        val title: String?,
        val genres: String?,
        val keywords: String?,
        val isShow: Boolean
    )

    @Serializable
    data class PersonScreen(
        val personId: Int,
        val personProfilePath: String?,
    )
}