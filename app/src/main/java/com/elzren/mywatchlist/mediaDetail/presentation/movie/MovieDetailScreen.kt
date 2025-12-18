package com.elzren.mywatchlist.mediaDetail.presentation.movie

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.elzren.mywatchlist.core.presentation.composables.Heading
import com.elzren.mywatchlist.core.presentation.composables.HorizontalFeed
import com.elzren.mywatchlist.core.presentation.composables.InfoItem
import com.elzren.mywatchlist.core.presentation.composables.MediaPosterClickable
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.ContextUtils.copyToClipboard
import com.elzren.mywatchlist.core.utils.ContextUtils.showToast
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl
import com.elzren.mywatchlist.core.utils.Utils.defaultPlaceholder
import com.elzren.mywatchlist.core.utils.Utils.nonZeroOrNull
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.presentation.composables.CastItem
import com.elzren.mywatchlist.mediaDetail.presentation.composables.CastItemPlaceholder
import com.elzren.mywatchlist.mediaDetail.presentation.composables.GenresRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.GenresRowPlaceholder
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
    LaunchedEffect(key1 = movieId) {
        viewModel.getMovieData(movieId)
    }

    val movieDetailUiState by viewModel.uiState.collectAsState()

    MovieDetailScreenContent(
        uiState = movieDetailUiState,
        navActionManager = navActionManager,
        viewModel = viewModel,
        modifier = modifier
    )
}

@Composable
fun MovieDetailScreenContent(
    uiState: MovieDetailUiState,
    navActionManager: NavActionManager,
    viewModel: MovieDetailViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            context.showToast(context.getString(uiState.errorMessage))
        }
    }

    MediaDetailScaffold(
        title = uiState.movieDetail?.title,
        navActionManager = navActionManager
    ) { padding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = padding.calculateBottomPadding())
                .padding(bottom = 80.dp),
        ) {
            Box {
                MediaBanner(bannerUrl = uiState.movieDetail?.backdropPath?.toTmdbImgUrl("original"))
                PosterRow(posterUrl = uiState.movieDetail?.posterPath?.toTmdbImgUrl()) {
                    MediaTitle(
                        title = uiState.movieDetail?.title ?: stringResource(R.string.loading),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .defaultPlaceholder(visible = uiState.isLoading)
                            .combinedClickable(
                                onLongClick = {
                                    uiState.movieDetail?.title?.let {
                                        context.copyToClipboard(it)
                                    }
                                },
                                onClick = {}
                            )
                    )
                    InfoRow(
                        mediaType = "movie",
                        releaseDate = uiState.movieDetail?.releaseDate,
                        originalLanguage = uiState.movieDetail?.originalLanguage,
                        voteAverage = uiState.movieDetail?.voteAverage,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .defaultPlaceholder(visible = uiState.isLoading),
                    )
                    WatchlistButton(
                        isInWatchlist = uiState.isInWatchlist,
                        onClick = {
                            uiState.movieDetail?.let {
                                if (uiState.isInWatchlist) {
                                    viewModel.removeFromWatchlist(it.id)
                                } else {
                                    viewModel.addToWatchlist(it)
                                }
                            }
                        },
                        modifier = Modifier.defaultPlaceholder(visible = uiState.isLoading)
                    )
                }
            }

            if (uiState.isLoading) {
                GenresRowPlaceholder()
            } else {
                GenresRow(
                    genres = uiState.movieDetail?.genres.orEmpty(),
                    navActionManager = navActionManager,
                )
            }

            Synopsis(
                synopsis = uiState.movieDetail?.overview ?: stringResource(R.string.lorem_ipsun),
                textModifier = Modifier.defaultPlaceholder(visible = uiState.isLoading)
            )

            if (uiState.movieCast.isNotEmpty() || uiState.isCastLoading) {
                Column {
                    Heading(title = stringResource(R.string.cast))
                    if (uiState.isCastLoading) {
                        HorizontalFeed(items = List(10) { it }) {
                            CastItemPlaceholder()
                        }
                    } else {
                        HorizontalFeed(items = uiState.movieCast, itemContent = { cast ->
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
            }

            if (uiState.movieDetail != null) {
                Heading(title = stringResource(R.string.info))
                MovieInfo(uiState.movieDetail)
            }

            if (uiState.movieTrailer.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.trailer))
                    TrailerRow(uiState.movieTrailer)
                }
            }

            if (uiState.movieKeywords.isNotEmpty()) {
                Heading(title = stringResource(R.string.tags))
                Keywords(keywords = uiState.movieKeywords, navActionManager = navActionManager)
            }

            if (uiState.movieRecommendations.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.recommendations))
                    HorizontalFeed(items = uiState.movieRecommendations, itemContent = { recommendation ->
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