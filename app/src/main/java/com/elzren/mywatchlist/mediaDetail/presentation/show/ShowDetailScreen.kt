package com.elzren.mywatchlist.mediaDetail.presentation.show

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
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
fun ShowDetailScreen(
    showId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getShowData(showId)
    }
    val showDetailUiState by viewModel.uiState.collectAsState()

    with(showDetailUiState) {
        if (errorMessage != null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(errorMessage))
            }
        } else if (showDetail == null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            ShowDetailScreenContent(
                showDetail = showDetail,
                navActionManager = navActionManager,
                isInWatchlist = isInWatchlist,
                showCast = showCast,
                showRecommendations = showRecommendations,
                showKeywords = showKeywords,
                showTrailer = showTrailer,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreenContent(
    showDetail: ShowDetail,
    navActionManager: NavActionManager,
    isInWatchlist: Boolean,
    showCast: List<Cast>,
    showRecommendations: List<Media>,
    showKeywords: List<Keyword>,
    showTrailer: List<Video>,
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    MediaDetailScaffold(title = showDetail.name, navActionManager = navActionManager) { padding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = padding.calculateBottomPadding())
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box {
                MediaBanner(bannerUrl = showDetail.backdropPath?.toTmdbImgUrl("original"))
                PosterRow(posterUrl = showDetail.posterPath?.toTmdbImgUrl()) {
                    MediaTitle(
                        title = showDetail.name,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .combinedClickable(
                                onLongClick = { context.copyToClipboard(showDetail.name) },
                                onClick = {}
                            )
                    )
                    InfoRow(
                        mediaType = "tv",
                        releaseDate = showDetail.firstAirDate,
                        originalLanguage = showDetail.originalLanguage,
                        voteAverage = showDetail.voteAverage,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                    WatchlistButton(
                        isInWatchlist = isInWatchlist,
                        onClick = {
                            if (isInWatchlist) {
                                viewModel.removeFromWatchlist(showDetail.id)
                            } else {
                                viewModel.addToWatchlist(showDetail)
                            }
                        }
                    )
                }
            }
            GenresRow(
                genres = showDetail.genres,
                navActionManager = navActionManager,
                isShow = true
            )
            Synopsis(synopsis = showDetail.overview)

            if (showCast.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.cast))
                    HorizontalFeed(items = showCast, itemContent = { cast ->
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

            if (showTrailer.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.trailer))
                    TrailerRow(showTrailer)
                }
            }

            Heading(title = stringResource(R.string.info))
            ShowInfo(showDetail)

            if (showKeywords.isNotEmpty()) {
                Heading(title = stringResource(R.string.tags))
                Keywords(
                    keywords = showKeywords,
                    navActionManager = navActionManager,
                    isShow = true
                )
            }

            if (showRecommendations.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.recommendations))
                    HorizontalFeed(items = showRecommendations, itemContent = { recommendation ->
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
                info = firstAirDate.let { Utils.formatDate(it) }
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