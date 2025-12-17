package com.elzren.mywatchlist.mediaDetail.presentation.movie

import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.domain.model.Media
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.Heading
import com.elzren.mywatchlist.core.presentation.composables.HorizontalFeed
import com.elzren.mywatchlist.core.presentation.composables.InfoItem
import com.elzren.mywatchlist.core.presentation.composables.MediaPosterClickable
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.ContextUtils.copyToClipboard
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl
import com.elzren.mywatchlist.core.utils.Utils.nonZeroOrNull
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.Video
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
import com.elzren.mywatchlist.mediaDetail.presentation.composables.TrailerRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.WatchlistButton
import com.elzren.mywatchlist.mediaDetail.utils.Utils

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieData(movieId)
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
                movieTrailer = movieTrailer,
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
    movieTrailer: List<Video>,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

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
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .combinedClickable(
                                onLongClick = { context.copyToClipboard(movieDetail.title) },
                                onClick = {}
                            )
                    )
                    InfoRow(
                        mediaType = "movie",
                        releaseDate = movieDetail.releaseDate,
                        originalLanguage = movieDetail.originalLanguage,
                        voteAverage = movieDetail.voteAverage,
                        modifier = Modifier.padding(vertical = 8.dp),
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
                            playedBy = cast.name,
                            navActionManager = navActionManager
                        )
                    })
                }
            }

            if (movieTrailer.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.trailer))
                    TrailerRow(movieTrailer)
                }
            }

            Heading(title = stringResource(R.string.info))
            MovieInfo(movieDetail)

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

@Composable
private fun MovieInfo(movieDetail: MovieDetail, modifier: Modifier = Modifier) {
    with(movieDetail) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier) {
            InfoItem(
                title = stringResource(R.string.language),
                info = originalLanguage.let { Utils.getLanguageName(it) }
            )
            InfoItem(
                title = stringResource(R.string.status),
                info = status
            )
            InfoItem(
                title = stringResource(R.string.release_date),
                info = releaseDate.let { Utils.formatDate(it) }
            )
            InfoItem(
                title = stringResource(R.string.runtime),
                info = runtime.nonZeroOrNull()?.let { Utils.formatDuration(it) }
            )
            InfoItem(
                title = stringResource(R.string.budget),
                info = budget.nonZeroOrNull()?.let { Utils.formatAmount(it) }
            )
            InfoItem(
                title = stringResource(R.string.box_office),
                info = revenue.nonZeroOrNull()?.let { Utils.formatAmount(it) }
            )
        }
    }
}