package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.utils.StringUtils.capitalizeFirstChar

@Composable
fun InfoRow(
    mediaType: String,
    releaseDate: String,
    originalLanguage: String,
    voteAverage: Double,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        Text(text = mediaType.capitalizeFirstChar(), color = color)
        DotSeparator(color = color)

        if (releaseDate.isNotBlank()) {
            Text(text = releaseDate.substringBefore('-'), color = color)
            DotSeparator(color = color)
        }

        Text(text = originalLanguage.capitalizeFirstChar(), color = color)
        DotSeparator(color = color)

        TextWithIcon(
            icon = R.drawable.star_20px,
            text = voteAverage.toString().substring(0, 3),
            color = color
        )
    }
}

@Composable
private fun DotSeparator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        text = "•",
        modifier = modifier.padding(horizontal = 8.dp),
        color = color
    )
}