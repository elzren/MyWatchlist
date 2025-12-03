package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.elzren.mywatchlist.core.utils.Constants

@Composable
fun MediaPoster(
    posterUrl: String?,
    modifier: Modifier = Modifier,
    width: Dp = Constants.MEDIA_POSTER_WIDTH.dp,
    height: Dp = Constants.MEDIA_POSTER_HEIGHT.dp,
) {
    AsyncImage(
        model = posterUrl,
        contentDescription = "Poster",
        contentScale = ContentScale.Crop,
        fallback = ColorPainter(MaterialTheme.colorScheme.outline),
        placeholder = ColorPainter(MaterialTheme.colorScheme.outline),
        error = ColorPainter(MaterialTheme.colorScheme.outline),
        modifier = modifier
            .size(width, height)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun MediaPosterClickable(
    posterUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        MediaPoster(posterUrl = posterUrl)
    }
}

@Composable
fun MediaPosterSmall(
    posterUrl: String?,
    modifier: Modifier = Modifier,
) {
    MediaPoster(
        posterUrl = posterUrl,
        modifier = modifier,
        width = 110.dp,
        height = 165.dp
    )
}