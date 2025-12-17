package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.core.utils.Constants

@Composable
fun MediaPoster(
    posterUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    width: Dp = Constants.MEDIA_POSTER_WIDTH.dp,
    height: Dp = Constants.MEDIA_POSTER_HEIGHT.dp,
) {
    Image(
        imageUrl = posterUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(width, height)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun MediaPosterClickable(
    posterUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        MediaPoster(posterUrl = posterUrl, contentDescription = contentDescription)
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