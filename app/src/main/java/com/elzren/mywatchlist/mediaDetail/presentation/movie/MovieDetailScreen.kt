package com.elzren.mywatchlist.mediaDetail.presentation.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.domain.model.Media
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.Heading
import com.elzren.mywatchlist.core.presentation.composables.HorizontalFeed
import com.elzren.mywatchlist.core.presentation.composables.MediaPosterClickable
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Cast
import com.elzren.mywatchlist.mediaDetail.domain.model.keyword.Keyword
import com.elzren.mywatchlist.mediaDetail.presentation.composables.CastItem
import com.elzren.mywatchlist.mediaDetail.presentation.composables.GenresRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.InfoRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.Keywords
import com.elzren.mywatchlist.mediaDetail.presentation.composables.MediaBanner
import com.elzren.mywatchlist.mediaDetail.presentation.composables.MediaDetailScaffold
import com.elzren.mywatchlist.mediaDetail.presentation.composables.MediaTitle
import com.elzren.mywatchlist.mediaDetail.presentation.composables.PosterRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.Synopsis
import com.elzren.mywatchlist.mediaDetail.presentation.composables.WatchlistButton

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetail(movieId)
        viewModel.getWatchlistStatus(movieId)
        viewModel.getMovieCast(movieId)
        viewModel.getMovieRecommendations(movieId)
        viewModel.getMovieKeywords(movieId)
    }
    val movieDetailUiState by viewModel.uiState.collectAsState()

    with(movieDetailUiState) {
        if (errorMessage != null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(errorMessage))
            }
        } else if (movieDetail == null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            MovieDetailScreenContent(
                movieDetail = movieDetail,
                navActionManager = navActionManager,
                isInWatchlist = isInWatchlist,
                movieCast = movieCast,
                movieRecommendations = movieRecommendations,
                movieKeywords = movieKeywords,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun MovieDetailScreenContent(
    movieDetail: MovieDetail,
    navActionManager: NavActionManager,
    isInWatchlist: Boolean,
    movieCast: List<Cast>,
    movieRecommendations: List<Media>,
    movieKeywords: List<Keyword>,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    MediaDetailScaffold(title = movieDetail.title, navActionManager = navActionManager) { padding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = padding.calculateBottomPadding())
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box {
                MediaBanner(bannerUrl = movieDetail.backdropPath?.toTmdbImgUrl("original"))
                PosterRow(posterUrl = movieDetail.posterPath?.toTmdbImgUrl()) {
                    MediaTitle(
                        title = movieDetail.title,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    InfoRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                        infoList = listOf(
                            "Movie",
                            movieDetail.releaseDate.substringBefore('-'),
                            movieDetail.originalLanguage.uppercase(),
                            movieDetail.voteAverage.toString().substring(0, 3),
                        )
                    )
                    WatchlistButton(
                        isInWatchlist = isInWatchlist,
                        onClick = {
                            if (isInWatchlist) {
                                viewModel.removeFromWatchlist(movieDetail.id)
                            } else {
                                viewModel.addToWatchlist(movieDetail)
                            }
                        }
                    )
                }
            }
            GenresRow(genres = movieDetail.genres, navActionManager = navActionManager)
            Synopsis(synopsis = movieDetail.overview)

            if (movieCast.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.cast))
                    HorizontalFeed(items = movieCast, itemContent = { cast ->
                        CastItem(
                            id = cast.id,
                            profilePath = cast.profilePath,
                            characterName = cast.character,
                            playedBy = cast.name
                        )
                    })
                }
            }

            if (movieKeywords.isNotEmpty()) {
                Heading(title = stringResource(R.string.tags))
                Keywords(keywords = movieKeywords, navActionManager = navActionManager)
            }

            if (movieRecommendations.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.recommendations))
                    HorizontalFeed(items = movieRecommendations, itemContent = { recommendation ->
                        MediaPosterClickable(
                            posterUrl = recommendation.posterPath?.toTmdbImgUrl(),
                            onClick = {
                                if (recommendation.mediaType == "movie") navActionManager.toMovieDetail(
                                    recommendation.id
                                ) else navActionManager.toShowDetail(
                                    recommendation.id
                                )
                            }
                        )
                    })
                }
            }
        }
    }
}



