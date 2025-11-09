package com.example.playground.core.presentation.navigation

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Home

    @Serializable
    object Search

    @Serializable
    data class MovieDetail(val id: Int)
}