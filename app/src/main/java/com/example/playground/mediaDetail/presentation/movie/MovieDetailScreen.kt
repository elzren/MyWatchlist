package com.example.playground.mediaDetail.presentation.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.playground.R
import com.example.playground.core.presentation.composables.CenterAlignedBox
import com.example.playground.core.presentation.navigation.NavActionManager
import com.example.playground.core.utils.toTmdbImgUrl
import com.example.playground.mediaDetail.domain.model.MovieDetail

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
                modifier = modifier,
            )
        }
    }
}

@Composable
fun MovieDetailScreenContent(movieDetail: MovieDetail, modifier: Modifier = Modifier) {
    Surface {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = movieDetail.backdropPath.toTmdbImgUrl("original"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier.fillMaxSize()
        )
    }
    }
}