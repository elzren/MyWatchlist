package com.elzren.mywatchlist.mediaDetail.presentation.show

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.elzren.mywatchlist.core.utils.Utils.nonBlankOrNull
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
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
fun ShowDetailScreen(
    showId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = showId) {
        viewModel.getShowData(showId)
    }

    val showDetailUiState by viewModel.uiState.collectAsState()

    ShowDetailScreenContent(
        uiState = showDetailUiState,
        navActionManager = navActionManager,
        viewModel = viewModel,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreenContent(
    uiState: ShowDetailUiState,
    navActionManager: NavActionManager,
    viewModel: ShowDetailViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            context.showToast(context.getString(uiState.errorMessage))
        }
    }

    MediaDetailScaffold(
        title = uiState.showDetail?.name,
        navActionManager = navActionManager
    ) { padding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = padding.calculateBottomPadding())
                .padding(bottom = 80.dp),
        ) {
            Box {
                MediaBanner(bannerUrl = uiState.showDetail?.backdropPath?.toTmdbImgUrl("original"))
                PosterRow(posterUrl = uiState.showDetail?.posterPath?.toTmdbImgUrl()) {
                    MediaTitle(
                        title = uiState.showDetail?.name ?: stringResource(R.string.loading),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .defaultPlaceholder(visible = uiState.isLoading)
                            .combinedClickable(
                                onLongClick = {
                                    uiState.showDetail?.name?.let {
                                        context.copyToClipboard(it)
                                    }
                                },
                                onClick = {}
                            )
                    )
                    InfoRow(
                        mediaType = "tv",
                        releaseDate = uiState.showDetail?.firstAirDate,
                        originalLanguage = uiState.showDetail?.originalLanguage,
                        voteAverage = uiState.showDetail?.voteAverage,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .defaultPlaceholder(visible = uiState.isLoading),
                    )
                    WatchlistButton(
                        isInWatchlist = uiState.isInWatchlist,
                        onClick = {
                            uiState.showDetail?.let {
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
                    genres = uiState.showDetail?.genres.orEmpty(),
                    navActionManager = navActionManager,
                    isShow = true
                )
            }

            Synopsis(
                synopsis = uiState.showDetail?.overview ?: stringResource(R.string.lorem_ipsun),
                textModifier = Modifier.defaultPlaceholder(visible = uiState.isLoading)
            )

            if (uiState.showCast.isNotEmpty() || uiState.isCastLoading) {
                Column {
                    Heading(title = stringResource(R.string.cast))
                    if (uiState.isCastLoading) {
                        HorizontalFeed(items = List(10) { it }) {
                            CastItemPlaceholder()
                        }
                    } else {
                        HorizontalFeed(items = uiState.showCast, itemContent = { cast ->
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

            if (uiState.showDetail != null) {
                Heading(title = stringResource(R.string.info))
                ShowInfo(uiState.showDetail)
            }

            if (uiState.showTrailer.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.trailer))
                    TrailerRow(uiState.showTrailer)
                }
            }

            if (uiState.showKeywords.isNotEmpty()) {
                Heading(title = stringResource(R.string.tags))
                Keywords(
                    keywords = uiState.showKeywords,
                    navActionManager = navActionManager,
                    isShow = true
                )
            }

            if (uiState.showRecommendations.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.recommendations))
                    HorizontalFeed(
                        items = uiState.showRecommendations,
                        itemContent = { recommendation ->
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
private fun ShowInfo(showDetail: ShowDetail, modifier: Modifier = Modifier) {
    with(showDetail) {
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
                title = stringResource(R.string.first_air_date),
                info = firstAirDate.nonBlankOrNull()?.let { Utils.formatDate(it) }
            )
            InfoItem(
                title = stringResource(R.string.last_air_date),
                info = lastAirDate?.let { Utils.formatDate(it) }
            )
            InfoItem(
                title = stringResource(R.string.seasons),
                info = numberOfSeasons.toString()
            )
            InfoItem(
                title = stringResource(R.string.episodes),
                info = numberOfEpisodes.toString()
            )
            InfoItem(
                title = stringResource(R.string.created_by),
                info = createdBy.getOrNull(0)?.name
            )
        }
    }
}