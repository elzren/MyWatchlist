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
}