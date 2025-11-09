package com.example.playground.core.presentation.navigation

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

    fun toMovieDetail(id: Int) {
        navController.navigate(Routes.MovieDetail(id))
    }

    fun toSearch() {
        navController.navigate(Routes.Search)
    }
}