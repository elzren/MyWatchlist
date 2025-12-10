package com.elzren.mywatchlist.person.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.HorizontalMediaItem
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.person.presentation.PersonUiState

@Composable
fun PersonMovies(
    personUiState: PersonUiState,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier
) {
    with(personUiState) {
        if (moviesErrorMessage != null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(moviesErrorMessage))
            }
        } else if (isPersonMoviesLoading) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = modifier.fillMaxHeight()
            ) {
                items(personMovies) { movie ->
                    with(movie) {
                        HorizontalMediaItem(
                            mediaType = "movie",
                            title = title,
                            posterPath = posterPath,
                            overview = overview,
                            releaseDate = releaseDate,
                            originalLanguage = originalLanguage,
                            voteAverage = voteAverage,
                            onClick = {
                                navActionManager.toMovieDetail(id)
                            },
                        )
                    }
                }
            }
        }
    }
}