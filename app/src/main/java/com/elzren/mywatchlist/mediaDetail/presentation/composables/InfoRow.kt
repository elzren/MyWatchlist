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
import com.elzren.mywatchlist.core.utils.Utils.nonZeroOrNull
import com.elzren.mywatchlist.mediaDetail.utils.Utils

@Composable
fun InfoRow(
    mediaType: String,
    releaseDate: String?,
    originalLanguage: String?,
    voteAverage: Double?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        Text(text = mediaType.capitalizeFirstChar(), color = color)

        if (!releaseDate.isNullOrBlank()) {
            DotSeparator(color = color)
            Text(text = releaseDate.substringBefore('-'), color = color)
        }

        if (!originalLanguage.isNullOrBlank()) {
            DotSeparator(color = color)
            Text(text = originalLanguage.capitalizeFirstChar(), color = color)
        }

        if (voteAverage?.nonZeroOrNull() != null) {
            DotSeparator(color = color)
            TextWithIcon(
                icon = R.drawable.star_20px,
                text = voteAverage.let { Utils.formatDecimal(it, "##.#") },
                color = color
            )
        }
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