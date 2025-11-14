package com.example.playground.mediaDetail.presentation.show

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.playground.R
import com.example.playground.core.presentation.composables.CenterAlignedBox
import com.example.playground.core.presentation.composables.ScaffoldWithTopAppBar
import com.example.playground.core.presentation.navigation.NavActionManager
import com.example.playground.core.utils.toTmdbImgUrl
import com.example.playground.mediaDetail.domain.model.ShowDetail
import com.example.playground.mediaDetail.presentation.composables.BackIconButton
import com.example.playground.mediaDetail.presentation.composables.MediaBanner
import com.example.playground.mediaDetail.presentation.composables.MediaDetailScaffold
import com.example.playground.mediaDetail.presentation.composables.MediaTitle
import com.example.playground.mediaDetail.presentation.composables.PosterInfoRow
import com.example.playground.mediaDetail.presentation.composables.TextWithIcon

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
            CenterAlignedBox {
                Text(text = "Error: $errorMessage")
            }
        } else if (showDetail == null) {
            CenterAlignedBox {
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
                .padding(bottom = 80.dp)
        ) {
            Box {
                MediaBanner(bannerUrl = showDetail.backdropPath.toTmdbImgUrl("original"))
                PosterInfoRow(posterUrl = showDetail.posterPath.toTmdbImgUrl()) {
                    MediaTitle(
                        title = showDetail.name,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        TextWithIcon(text = "TV", icon = R.drawable.live_tv_24px)
                        TextWithIcon(text = showDetail.firstAirDate, icon = R.drawable.calendar_month_24px)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        TextWithIcon(text = showDetail.voteAverage.toString(), icon = R.drawable.star_shine_24px)
                        TextWithIcon(text = showDetail.originalLanguage, icon = R.drawable.language_24px)
                    }
                }
            }

//           to check scroll behavior
            Box(
                modifier = Modifier
                    .height(1000.dp)
                    .fillMaxWidth()
            )
        }
    }
}