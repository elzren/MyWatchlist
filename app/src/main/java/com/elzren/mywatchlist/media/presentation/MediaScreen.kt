package com.elzren.mywatchlist.media.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.domain.model.Media
import com.elzren.mywatchlist.core.domain.model.MediaType
import com.elzren.mywatchlist.core.domain.model.TabItem
import com.elzren.mywatchlist.core.presentation.composables.BackIconButton
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.HorizontalMediaItem
import com.elzren.mywatchlist.core.presentation.composables.HorizontalPagerWithTabs
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.mapper.userMessage
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(
    navActionManager: NavActionManager,
    screenTitle: String?,
    genres: String?,
    keywords: String?,
    isShow: Boolean,
    modifier: Modifier = Modifier,
    viewModel: MediaViewModel = hiltViewModel()
) {
    val mediaUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        if (genres != mediaUiState.genres || keywords != mediaUiState.keywords) {
            viewModel.setGenresAndKeywords(genres, keywords)
            viewModel.getMedia()
        }
    }

    val movies = mediaUiState.movies.collectAsLazyPagingItems()
    val shows = mediaUiState.shows.collectAsLazyPagingItems()
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val tabs = remember {
        MediaType.entries.map { TabItem(value = it, title = it.string) }
    }

    ScaffoldWithTopAppBar(
        title = screenTitle,
        scrollBehavior = topAppBarScrollBehavior,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
        navigationIcon = { BackIconButton(onClick = navActionManager::goBack) },
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { padding ->
        HorizontalPagerWithTabs (
            tabs = tabs,
            initialPage = if (isShow) MediaType.SHOW.ordinal else MediaType.MOVIE.ordinal,
            modifier = Modifier.padding(padding),
        ) { page ->
            MediaContent(
                mediaItems = when (page) {
                    MediaType.SHOW.ordinal -> shows
                    else -> movies
                },
                modifier = Modifier.padding(top = 16.dp),
                navActionManager = navActionManager
            )
        }
    }
}

@Composable
fun MediaContent(
    mediaItems: LazyPagingItems<Media>,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        when {
            (mediaItems.itemCount > 0) -> {
                items(mediaItems.itemCount) { index ->
                    val media = mediaItems[index]
                    if (media != null) {
                        with(media) {
                            HorizontalMediaItem(
                                mediaType = this.mediaType,
                                title = title,
                                posterPath = posterPath,
                                overview = overview,
                                releaseDate = releaseDate,
                                originalLanguage = originalLanguage,
                                voteAverage = voteAverage,
                                onClick = {
                                    if (mediaType == "movie") navActionManager.toMovieDetail(
                                        id
                                    ) else navActionManager.toShowDetail(
                                        id
                                    )
                                },
                            )
                        }
                    }
                }
            }

            mediaItems.loadState.refresh is LoadState.Loading -> {
                item {
                    CenteredBox(modifier = Modifier.fillParentMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            }

            mediaItems.loadState.refresh is LoadState.Error -> {
                item {
                    CenteredBox(modifier = Modifier.fillParentMaxSize()) {
                        Text(
                            text = stringResource((mediaItems.loadState.refresh as LoadState.Error).error.userMessage())
                        )
                    }
                }
            }

            mediaItems.loadState.refresh is LoadState.NotLoading -> {
                if (mediaItems.itemCount == 0) {
                    item {
                        CenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            Text(text = stringResource(R.string.no_results_found))
                        }
                    }
                }
            }
        }

        when (mediaItems.loadState.append) {
            is LoadState.Loading -> {
                item {
                    CenteredBox(modifier = Modifier.fillParentMaxWidth()) {
                        CircularProgressIndicator()
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    CenteredBox(modifier = Modifier.fillParentMaxWidth()) {
                        Text(text = stringResource((mediaItems.loadState.append as LoadState.Error).error.userMessage()))
                    }
                }
            }

            else -> {}
        }
    }
}
