package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun Image(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = contentScale,
        fallback = ColorPainter(MaterialTheme.colorScheme.outline),
        placeholder = ColorPainter(MaterialTheme.colorScheme.outline),
        error = ColorPainter(MaterialTheme.colorScheme.outline),
        modifier = modifier
    )
}