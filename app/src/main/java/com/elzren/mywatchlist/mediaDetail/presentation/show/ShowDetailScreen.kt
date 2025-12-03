package com.elzren.mywatchlist.mediaDetail.presentation.show

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
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.model.credit.Cast
import com.elzren.mywatchlist.mediaDetail.presentation.composables.CastItem
import com.elzren.mywatchlist.mediaDetail.presentation.composables.GenresRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.InfoRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.MediaBanner
import com.elzren.mywatchlist.mediaDetail.presentation.composables.MediaDetailScaffold
import com.elzren.mywatchlist.mediaDetail.presentation.composables.MediaTitle
import com.elzren.mywatchlist.mediaDetail.presentation.composables.PosterRow
import com.elzren.mywatchlist.mediaDetail.presentation.composables.Synopsis
import com.elzren.mywatchlist.mediaDetail.presentation.composables.WatchlistButton

@Composable
fun ShowDetailScreen(
    showId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getShowDetail(showId)
        viewModel.getWatchlistStatus(showId)
        viewModel.getShowCast(showId)
        viewModel.getShowRecommendations(showId)
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
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = hiltViewModel()
) {
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
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    InfoRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                        infoList = listOf(
                            "Tv",
                            showDetail.firstAirDate.substringBefore('-'),
                            showDetail.originalLanguage.uppercase(),
                            showDetail.voteAverage.toString().substring(0, 3),
                        )
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
            GenresRow(genres = showDetail.genres)
            Synopsis(synopsis = showDetail.overview)

            if (showCast.isNotEmpty()) {
                Column {
                    Heading(title = stringResource(R.string.cast))
                    HorizontalFeed(items = showCast, itemContent = { cast ->
                        CastItem(
                            id = cast.id,
                            profilePath = cast.profilePath,
                            characterName = cast.character,
                            playedBy = cast.name
                        )
                    })
                }
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

            // to check scroll behavior
//            Box(
//                modifier = Modifier
//                    .height(1000.dp)
//                    .fillMaxWidth()
//            )
        }
    }
}