package com.example.playground.mediaDetail.presentation.show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.playground.core.presentation.composables.CenteredBox
import com.example.playground.core.presentation.navigation.NavActionManager
import com.example.playground.core.utils.toTmdbImgUrl
import com.example.playground.mediaDetail.domain.model.ShowDetail
import com.example.playground.mediaDetail.presentation.composables.GenresRow
import com.example.playground.mediaDetail.presentation.composables.InfoRow
import com.example.playground.mediaDetail.presentation.composables.MediaBanner
import com.example.playground.mediaDetail.presentation.composables.MediaDetailScaffold
import com.example.playground.mediaDetail.presentation.composables.MediaTitle
import com.example.playground.mediaDetail.presentation.composables.PosterRow
import com.example.playground.mediaDetail.presentation.composables.Synopsis

@Composable
fun ShowDetailScreen(
    showId: Int,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getShowDetail(showId)
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
    modifier: Modifier = Modifier
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
                MediaBanner(bannerUrl = showDetail.backdropPath.toTmdbImgUrl("original"))
                PosterRow(posterUrl = showDetail.posterPath.toTmdbImgUrl()) {
                    MediaTitle(
                        title = showDetail.name,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    InfoRow(
                        infoList = listOf(
                            "Tv",
                            showDetail.firstAirDate.substringBefore('-'),
                            showDetail.originalLanguage.uppercase(),
                            showDetail.voteAverage.toString().substring(0, 3),
                        )
                    )
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp)
                    ) { Text(text = "Add to Watchlist") }
                }
            }
            GenresRow(genres = showDetail.genres)
            Synopsis(synopsis = showDetail.overview)

            // to check scroll behavior
            Box(
                modifier = Modifier
                    .height(1000.dp)
                    .fillMaxWidth()
            )
        }
    }
}