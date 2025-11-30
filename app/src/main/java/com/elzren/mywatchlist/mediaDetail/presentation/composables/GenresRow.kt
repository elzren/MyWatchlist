package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.mediaDetail.domain.model.Genre
import kotlin.collections.forEach

@Composable
fun GenresRow(genres: List<Genre>, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        genres.forEach { genre ->
            AssistChip(
                onClick = {},
                label = { Text(text = genre.name) }
            )
        }
    }
}