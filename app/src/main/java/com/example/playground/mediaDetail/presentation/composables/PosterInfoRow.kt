package com.example.playground.mediaDetail.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playground.core.presentation.composables.MediaPoster

@Composable
fun PosterInfoRow(
    posterUrl: String,
    modifier: Modifier = Modifier,
    infoContent: @Composable ColumnScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 150.dp)
    ) {
        MediaPoster(
            posterUrl = posterUrl,
            width = 150.dp,
            height = 225.dp,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(bottom = 16.dp),
            content = infoContent
        )
    }
}