package com.example.playground.core.presentation.composables

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
import com.example.playground.core.utils.Constants

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