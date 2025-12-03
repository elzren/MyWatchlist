package com.elzren.mywatchlist.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.Heading
import com.elzren.mywatchlist.core.presentation.composables.HorizontalFeed
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.Constants
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
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            with(homeUiState) {
                if (errorMessage != null) {
                    CenteredBox(modifier = Modifier.fillMaxSize()) {
                        Text(text = stringResource(errorMessage))
                    }
                } else if (isLoading) {
                    CenteredBox(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                } else {
                    HomeScreenContent(
                        homeUiState = this,
                        navActionManager = navActionManager
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
    navActionManager: NavActionManager
) {
    LazyColumn(modifier = modifier) {
        item {
            Heading(title = "Trending Movies", modifier = Modifier.padding(top = 8.dp))
            HorizontalFeed(items = homeUiState.trendingMovies, itemContent = { movie ->
                HorizontalFeedItem(
                    posterUrl = movie.posterPath?.toTmdbImgUrl(),
                    title = movie.title,
                    onClick = { navActionManager.toMovieDetail(movie.id) }
                )
            })
        }

        item {
            Heading(title = "Trending Shows", modifier = Modifier.padding(top = 8.dp))
            HorizontalFeed(items = homeUiState.trendingShows, itemContent = { show ->
                HorizontalFeedItem(
                    posterUrl = show.posterPath?.toTmdbImgUrl(),
                    title = show.name,
                    onClick = { navActionManager.toShowDetail(show.id) }
                )
            })
        }

        item {
            Heading(title = "Popular Movies", modifier = Modifier.padding(top = 8.dp))
            HorizontalFeed(items = homeUiState.popularMovies, itemContent = { movie ->
                HorizontalFeedItem(
                    posterUrl = movie.posterPath?.toTmdbImgUrl(),
                    title = movie.title,
                    onClick = { navActionManager.toMovieDetail(movie.id) }
                )
            })
        }

        item {
            Heading(title = "Popular Shows", modifier = Modifier.padding(top = 8.dp))
            HorizontalFeed(items = homeUiState.popularShows, itemContent = { show ->
                HorizontalFeedItem(
                    posterUrl = show.posterPath?.toTmdbImgUrl(),
                    title = show.name,
                    onClick = { navActionManager.toShowDetail(show.id) }
                )
            })
        }
    }
}

@Composable
fun HorizontalFeedItem(
    posterUrl: String?,
    modifier: Modifier = Modifier,
    title: String? = null,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .width(Constants.MEDIA_POSTER_WIDTH.dp)
            .height(Constants.MEDIA_POSTER_HEIGHT.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier.fillMaxSize()
        )
    }
}