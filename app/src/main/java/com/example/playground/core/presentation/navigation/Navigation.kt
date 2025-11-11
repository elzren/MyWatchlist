package com.example.playground.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.playground.home.presentation.HomeScreen
import com.example.playground.mediaDetail.presentation.movie.MovieDetailScreen
import com.example.playground.mediaDetail.presentation.show.ShowDetailScreen
import com.example.playground.search.presentation.SearchScreen
import com.example.playground.settings.presentation.SettingsScreen
import com.example.playground.watchlist.presentation.WatchlistScreen

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
            val movieDetail: Routes.MovieDetail = backStackEntry.toRoute()
            MovieDetailScreen(movieId = movieDetail.movieId, navActionManager = navActionManager)
        }

        composable<Routes.ShowDetail> { backStackEntry ->
            val showDetail: Routes.ShowDetail = backStackEntry.toRoute()
            ShowDetailScreen(showId = showDetail.showId, navActionManager = navActionManager)
        }

        composable<Routes.Watchlist> {
            WatchlistScreen(navActionManager = navActionManager)
        }

        composable<Routes.Settings> {
            SettingsScreen(navActionManager = navActionManager)
        }
    }
}
