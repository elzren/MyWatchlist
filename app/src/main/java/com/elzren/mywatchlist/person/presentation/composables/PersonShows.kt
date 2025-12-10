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
fun PersonShows(
    personUiState: PersonUiState,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier
) {
    with(personUiState) {
        if (showsErrorMessage != null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(showsErrorMessage))
            }
        } else if (isPersonShowsLoading) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = modifier.fillMaxHeight()
            ) {
                items(personShows) { show ->
                    with(show) {
                        HorizontalMediaItem(
                            mediaType = "tv",
                            title = name,
                            posterPath = posterPath,
                            overview = overview,
                            releaseDate = firstAirDate,
                            originalLanguage = originalLanguage,
                            voteAverage = voteAverage,
                            onClick = {
                                navActionManager.toShowDetail(id)
                            },
                        )
                    }
                }
            }
        }
    }
}