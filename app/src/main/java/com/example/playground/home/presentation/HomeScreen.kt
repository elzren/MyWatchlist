package com.example.playground.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.playground.R
import com.example.playground.core.domain.model.Movie
import com.example.playground.core.domain.model.Show
import com.example.playground.core.utils.toTmdbImgUrl

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.uiState.collectAsState()

    if (homeUiState.errorMessage != null) {
        ScreenCenteredText(text = "Error: " + (homeUiState.errorMessage ?: "Something went wrong"))
    } else if (homeUiState.isLoading) {
        ScreenCenteredText(text = "Loading...")
    } else {
        HomeScreenContent(
            movies = homeUiState.trendingMovies,
            shows = homeUiState.trendingShows,
            modifier = modifier
        )
    }
}

@Composable
fun ScreenCenteredText(text: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = text)
    }
}

@Composable
fun HomeScreenContent(movies: List<Movie>, shows: List<Show>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            Column(modifier = Modifier) {
                Spacer(modifier = Modifier.height(8.dp))
                Heading(title = "Trending Movies")
                HorizontalFeed(items = movies, itemContent = { movie ->
                    HorizontalFeedItem(
                        posterUrl = movie.posterPath.toTmdbImgUrl(),
                        title = movie.title
                    )
                })
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Heading(title = "Trending Shows")
            HorizontalFeed(items = shows, itemContent = { show ->
                HorizontalFeedItem(
                    posterUrl = show.posterPath.toTmdbImgUrl(),
                    title = show.name
                )
            })
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Heading(title = "Popular Movies")
            HorizontalFeed(items = movies, itemContent = { movie ->
                HorizontalFeedItem(
                    posterUrl = movie.posterPath.toTmdbImgUrl(),
                    title = movie.title
                )
            })
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Heading(title = "Popular Shows")
            HorizontalFeed(items = shows, itemContent = { show ->
                HorizontalFeedItem(
                    posterUrl = show.posterPath.toTmdbImgUrl(),
                    title = show.name
                )
            })
        }
    }
}

@Composable
fun Heading(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun <T> HorizontalFeed(
    items: List<T>,
    itemContent: @Composable (LazyItemScope.(T) -> Unit),
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        items(items = items, itemContent = itemContent)
    }
}

@Composable
fun HorizontalFeedItem(
    posterUrl: String,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    Card(modifier = modifier
        .width(180.dp)
        .height(270.dp)) {
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