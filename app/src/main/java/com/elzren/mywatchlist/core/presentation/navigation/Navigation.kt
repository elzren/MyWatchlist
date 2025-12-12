package com.elzren.mywatchlist.core.presentation.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.elzren.mywatchlist.home.presentation.HomeScreen
import com.elzren.mywatchlist.media.presentation.MediaScreen
import com.elzren.mywatchlist.mediaDetail.presentation.movie.MovieDetailScreen
import com.elzren.mywatchlist.mediaDetail.presentation.show.ShowDetailScreen
import com.elzren.mywatchlist.person.presentation.PersonScreen
import com.elzren.mywatchlist.search.presentation.SearchScreen
import com.elzren.mywatchlist.settings.presentation.SettingsScreen
import com.elzren.mywatchlist.watchlist.presentation.WatchlistScreen

@Composable
fun Navigation(
    navController: NavHostController,
    navActionManager: NavActionManager,
    padding: PaddingValues
) {
    val bottomPadding by animateDpAsState(
        targetValue = padding.calculateBottomPadding(),
        label = "bottom_bar_padding"
    )

    NavHost(
        navController = navController,
        startDestination = Routes.Home,
    ) {
        composable<Routes.Home> {
            HomeScreen(
                navActionManager = navActionManager,
                modifier = Modifier.padding(bottom = bottomPadding)
            )
        }

        composable<Routes.Search> {
            SearchScreen(
                navActionManager = navActionManager,
                modifier = Modifier.padding(bottom = bottomPadding)
            )
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
            WatchlistScreen(
                navActionManager = navActionManager,
                modifier = Modifier.padding(bottom = bottomPadding)
            )
        }

        composable<Routes.Settings> {
            SettingsScreen(
                navActionManager = navActionManager,
                modifier = Modifier.padding(bottom = bottomPadding)
            )
        }

        composable<Routes.MediaScreen> { backStackEntry ->
            val mediaScreen: Routes.MediaScreen = backStackEntry.toRoute()
            MediaScreen(
                screenTitle = mediaScreen.title,
                genres = mediaScreen.genres,
                keywords = mediaScreen.keywords,
                isShow = mediaScreen.isShow,
                navActionManager = navActionManager,
            )
        }

        composable<Routes.PersonScreen> { backStackEntry ->
            val personScreen: Routes.PersonScreen = backStackEntry.toRoute()
            PersonScreen(
                personId = personScreen.personId,
                personProfilePath = personScreen.personProfilePath,
                navActionManager = navActionManager,
            )
        }
    }
}
