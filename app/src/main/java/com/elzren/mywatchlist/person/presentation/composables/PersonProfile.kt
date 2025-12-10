package com.elzren.mywatchlist.person.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.MediaPosterSmall
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl
import com.elzren.mywatchlist.person.domain.model.Person

@Composable
fun PersonProfile(
    personProfilePath: String?,
    personDetail: Person?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        MediaPosterSmall(
            posterUrl = personProfilePath?.toTmdbImgUrl(),
            modifier = Modifier.padding(end = 16.dp)
        )
        if (personDetail != null) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                Text(
                    text = personDetail.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row {
                    Text(
                        text = stringResource(R.string.gender_label),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = if (personDetail.gender == 1) {
                            stringResource(R.string.female)
                        } else {
                            stringResource(R.string.male)
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}