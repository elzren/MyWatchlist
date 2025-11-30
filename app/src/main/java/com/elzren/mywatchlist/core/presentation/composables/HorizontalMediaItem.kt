package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl
import com.elzren.mywatchlist.mediaDetail.presentation.composables.InfoRow

@Composable
fun HorizontalMediaItem(
    mediaType: String,
    title: String,
    posterPath: String?,
    overview: String,
    releaseDate: String,
    originalLanguage: String,
    voteAverage: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(onClick = { onClick() })
            .clip(RoundedCornerShape(8.dp))
    )
    {
        MediaPosterSmall(
            posterUrl = posterPath?.toTmdbImgUrl(),
        )
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            InfoRow(
                color = MaterialTheme.colorScheme.outline,
                infoList = listOf(
                    mediaType.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    },
                    releaseDate.substringBefore('-'),
                    originalLanguage.uppercase(),
                    voteAverage.toString().substring(0, 3),
                )
            )
            Text(
                text = overview,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}