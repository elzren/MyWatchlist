package com.elzren.mywatchlist.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

class NavActionManager(private val navController: NavHostController) {
    companion object {
        @Composable
        fun rememberNavActionManager(
            navController: NavHostController
        ) = remember {
            NavActionManager(navController)
        }
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun toMovieDetail(movieId: Int) {
        navController.navigate(Routes.MovieDetail(movieId))
    }

    fun toShowDetail(showId: Int) {
        navController.navigate(Routes.ShowDetail(showId))
    }

    fun toSearch() {
        navController.navigate(Routes.Search)
    }

    fun toMediaScreen(
        title: String? = null,
        genres: String? = null,
        keywords: String? = null,
        isShow: Boolean = false
    ) {
        navController.navigate(
            Routes.MediaScreen(
                title = title,
                genres = genres,
                keywords = keywords,
                isShow = isShow
            )
        )
    }

    fun toPersonScreen(
        personId: Int,
        personProfilePath: String?,
    ) {
        navController.navigate(
            Routes.PersonScreen(
                personId = personId,
                personProfilePath = personProfilePath
            )
        )
    }
}