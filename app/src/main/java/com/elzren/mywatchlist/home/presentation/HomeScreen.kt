package com.elzren.mywatchlist.home.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.Heading
import com.elzren.mywatchlist.core.presentation.composables.HorizontalFeed
import com.elzren.mywatchlist.core.presentation.composables.MediaPosterClickable
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navActionManager: NavActionManager
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    ScaffoldWithTopAppBar(
        title = stringResource(R.string.home),
        scrollBehavior = topAppBarScrollBehavior,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
        modifier = modifier
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            with(homeUiState) {
                if (errorMessage != null) {
                    HomeScreenError(error = errorMessage, onRetry = viewModel::retry)
                } else if (isLoading) {
                    CenteredBox(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                } else {
                    HomeScreenContent(
                        homeUiState = this,
                        navActionManager = navActionManager,
                        onRefresh = viewModel::refresh,
                        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
    navActionManager: NavActionManager,
    onRefresh: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = homeUiState.isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                Heading(title = stringResource(R.string.trending_movies), topPadding = 8.dp)
                HorizontalFeed(items = homeUiState.trendingMovies, itemContent = { movie ->
                    MediaPosterClickable(
                        posterUrl = movie.posterPath?.toTmdbImgUrl(),
                        contentDescription = movie.title,
                        onClick = { navActionManager.toMovieDetail(movie.id) }
                    )
                })
            }

            item {
                Heading(title = stringResource(R.string.trending_shows))
                HorizontalFeed(items = homeUiState.trendingShows, itemContent = { show ->
                    MediaPosterClickable(
                        posterUrl = show.posterPath?.toTmdbImgUrl(),
                        contentDescription = show.name,
                        onClick = { navActionManager.toShowDetail(show.id) }
                    )
                })
            }

            item {
                Heading(title = stringResource(R.string.popular_movies))
                HorizontalFeed(items = homeUiState.popularMovies, itemContent = { movie ->
                    MediaPosterClickable(
                        posterUrl = movie.posterPath?.toTmdbImgUrl(),
                        contentDescription = movie.title,
                        onClick = { navActionManager.toMovieDetail(movie.id) }
                    )
                })
            }

            item {
                Heading(title = stringResource(R.string.popular_shows))
                HorizontalFeed(items = homeUiState.popularShows, itemContent = { show ->
                    MediaPosterClickable(
                        posterUrl = show.posterPath?.toTmdbImgUrl(),
                        contentDescription = show.name,
                        onClick = { navActionManager.toShowDetail(show.id) }
                    )
                })
            }
        }
    }
}

@Composable
private fun HomeScreenError(
    @StringRes error: Int,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenteredBox(modifier = modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(error))
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(16.dp),
                shape = MaterialTheme.shapes.small
            ) { Text(stringResource(R.string.retry)) }
        }
    }
}