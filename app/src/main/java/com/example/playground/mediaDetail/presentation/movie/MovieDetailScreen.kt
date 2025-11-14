package com.example.playground.mediaDetail.presentation.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.playground.R
import com.example.playground.core.presentation.composables.CenterAlignedBox
import com.example.playground.core.presentation.navigation.NavActionManager
import com.example.playground.core.utils.toTmdbImgUrl
import com.example.playground.mediaDetail.domain.model.MovieDetail
import com.example.playground.mediaDetail.presentation.composables.MediaBanner
import com.example.playground.mediaDetail.presentation.composables.MediaDetailScaffold
import com.example.playground.mediaDetail.presentation.composables.MediaTitle
import com.example.playground.mediaDetail.presentation.composables.PosterInfoRow
import com.example.playground.mediaDetail.presentation.composables.TextWithIcon

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetail(movieId)
    }
    val movieDetailUiState by viewModel.uiState.collectAsState()

    with(movieDetailUiState) {
        if (errorMessage != null) {
            CenterAlignedBox {
                Text(text = "Error: $errorMessage")
            }
        } else if (movieDetail == null) {
            CenterAlignedBox {
                CircularProgressIndicator()
            }
        } else {
            MovieDetailScreenContent(
                movieDetail = movieDetail,
                navActionManager = navActionManager,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun MovieDetailScreenContent(
    movieDetail: MovieDetail,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier
) {
    MediaDetailScaffold(title = movieDetail.title, navActionManager = navActionManager) { padding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = padding.calculateBottomPadding())
                .padding(bottom = 80.dp)
        ) {
            Box {
                MediaBanner(bannerUrl = movieDetail.backdropPath.toTmdbImgUrl("original"))
                PosterInfoRow(posterUrl = movieDetail.posterPath.toTmdbImgUrl()) {
                    MediaTitle(
                        title = movieDetail.title,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        TextWithIcon(text = "Movie", icon = R.drawable.movie_24px)
                        TextWithIcon(text = movieDetail.releaseDate, icon = R.drawable.calendar_month_24px)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        TextWithIcon(text = movieDetail.voteAverage.toString(), icon = R.drawable.star_shine_24px)
                        TextWithIcon(text = movieDetail.originalLanguage, icon = R.drawable.language_24px)
                    }
                }
            }
        }
    }
}



