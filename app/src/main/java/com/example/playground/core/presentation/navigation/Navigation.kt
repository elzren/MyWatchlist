package com.example.playground.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.playground.home.presentation.HomeScreen
import com.example.playground.mediaDetail.presentation.MovieDetailScreen
import com.example.playground.search.presentation.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        modifier = modifier
    ) {
        composable<Routes.Home> {
            HomeScreen(navActionManager = navActionManager)
        }

        composable<Routes.Search> {
            SearchScreen(navActionManager = navActionManager)
        }

        composable<Routes.MovieDetail> { backStackEntry ->
            val detail: Routes.MovieDetail = backStackEntry.toRoute()
            MovieDetailScreen(id = detail.id, navActionManager = navActionManager)
        }
    }
}
