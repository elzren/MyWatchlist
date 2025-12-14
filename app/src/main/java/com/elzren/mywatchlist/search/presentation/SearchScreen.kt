package com.elzren.mywatchlist.search.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.BackIconButton
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.CloseIconButton
import com.elzren.mywatchlist.core.presentation.composables.HorizontalMediaItem
import com.elzren.mywatchlist.core.presentation.composables.IconCard
import com.elzren.mywatchlist.core.presentation.mapper.userMessage
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.search.utils.SearchUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { MainSearchBar(navActionManager = navActionManager) },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding() + 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.genres),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            Genres(
                navActionManager = navActionManager,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSearchBar(
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchUiState by viewModel.uiState.collectAsState()
    val searchResults = searchUiState.searchResults.collectAsLazyPagingItems()
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val searchHorizontalPadding by animateDpAsState(
        targetValue = if (isSearchActive) 0.dp else 16.dp,
        label = "searchHorizontalPadding"
    )

    val onSearchActiveChange: (Boolean) -> Unit = {
        isSearchActive = it
        if (!isSearchActive) viewModel.resetQuery()
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = searchHorizontalPadding),
            shape = RoundedCornerShape(8.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchUiState.query,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = {},
                    expanded = isSearchActive,
                    onExpandedChange = onSearchActiveChange,
                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                    leadingIcon = {
                        if (isSearchActive) {
                            BackIconButton(onClick = {
                                isSearchActive = false
                                viewModel.resetQuery()
                            })
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.search_24px),
                                contentDescription = stringResource(R.string.search)
                            )
                        }
                    },
                    trailingIcon = {
                        if (isSearchActive && searchUiState.query.isNotEmpty()) {
                            CloseIconButton(
                                onClick = { viewModel.resetQuery() },
                                description = R.string.delete
                            )
                        }
                    },
                )
            },
            expanded = isSearchActive,
            onExpandedChange = onSearchActiveChange,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = modifier.fillMaxSize()
            ) {
                when {
                    (searchResults.itemCount > 0) -> {
                        items(searchResults.itemCount) { index ->
                            val media = searchResults[index]
                            if (media != null) {
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

                    searchResults.loadState.refresh is LoadState.Loading -> {
                        if (searchUiState.query.isNotBlank()) {
                            item {
                                CenteredBox(modifier = Modifier.fillParentMaxSize()) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                    searchResults.loadState.refresh is LoadState.Error -> {
                        item {
                            CenteredBox(modifier = Modifier.fillParentMaxSize()) {
                                Text(
                                    text = stringResource((searchResults.loadState.refresh as LoadState.Error).error.userMessage())
                                )
                            }
                        }
                    }

                    searchResults.loadState.refresh is LoadState.NotLoading -> {
                        if (searchResults.itemCount == 0) {
                            item {
                                CenteredBox(modifier = Modifier.fillParentMaxSize()) {
                                    Text(text = stringResource(R.string.no_results_found))
                                }
                            }
                        }
                    }
                }

                when (searchResults.loadState.append) {
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
                                Text(text = stringResource((searchResults.loadState.append as LoadState.Error).error.userMessage()))
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun Genres(navActionManager: NavActionManager, modifier: Modifier = Modifier) {
    val genres = remember { SearchUtils.genres }
    for (index in genres.indices) {
        val currentGenre = genres[index]
        val nextGenre =
            if (index + 1 < genres.size) genres[index + 1] else null

        if (index % 2 == 0) {
            Row(
                modifier = modifier
            ) {
                IconCard(
                    title = currentGenre.name,
                    icon = currentGenre.icon,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    onClick = {
                        navActionManager.toMediaScreen(
                            title = currentGenre.name,
                            genres = currentGenre.id,
                        )
                    }
                )
                if (nextGenre != null) {
                    IconCard(
                        title = nextGenre.name,
                        icon = nextGenre.icon,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        onClick = {
                            navActionManager.toMediaScreen(
                                title = nextGenre.name,
                                genres = nextGenre.id,
                            )
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}