package com.elzren.mywatchlist.watchlist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.HorizontalMediaItem
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.watchlist.data.local.WatchlistEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val watchlistUiState by viewModel.uiState.collectAsState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    ScaffoldWithTopAppBar(
        title = stringResource(R.string.watchlist),
        scrollBehavior = topAppBarScrollBehavior,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            with(watchlistUiState) {
                if (errorMessage != null) {
                    CenteredBox(modifier = Modifier.fillMaxSize()) {
                        Text(text = stringResource(errorMessage))
                    }
                } else if (isLoading) {
                    CenteredBox(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                } else {
                    WatchlistScreenContent(
                        watchlist = watchlistItems,
                        navActionManager = navActionManager
                    )
                }
            }
        }
    }
}

@Composable
fun WatchlistScreenContent(
    watchlist: List<WatchlistEntity>,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(watchlist) { media ->
            with(media) {
                HorizontalMediaItem(
                    mediaType = mediaType,
                    title = title,
                    posterPath = posterPath,
                    overview = overview,
                    releaseDate = releaseDate,
                    originalLanguage = originalLanguage,
                    voteAverage = voteAverage,
                    onClick = {
                        if (mediaType == "movie") {
                            navActionManager.toMovieDetail(tmdbId)
                        } else {
                            navActionManager.toShowDetail(tmdbId)
                        }
                    },
                )
            }
        }
    }
}