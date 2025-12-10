package com.elzren.mywatchlist.person.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.CenteredBox
import com.elzren.mywatchlist.core.presentation.composables.InfoItem
import com.elzren.mywatchlist.person.presentation.PersonUiState

@Composable
fun PersonOverview(personUiState: PersonUiState, modifier: Modifier = Modifier) {
    with(personUiState) {
        if (detailErrorMessage != null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(detailErrorMessage))
            }
        } else if (personDetail == null) {
            CenteredBox(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxHeight()
            ) {
                item {
                    InfoItem(
                        title = stringResource(R.string.birthday),
                        info = personDetail.birthday
                    )
                }
                item {
                    InfoItem(
                        title = stringResource(R.string.hometown),
                        info = personDetail.placeOfBirth
                    )
                }
                item {
                    InfoItem(
                        title = stringResource(R.string.known_for),
                        info = personDetail.knownForDepartment
                    )
                }
                item {
                    SelectionContainer {
                        Text(
                            text = personDetail.biography,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}