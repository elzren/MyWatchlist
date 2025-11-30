package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MediaBanner(bannerUrl: String?, modifier: Modifier = Modifier, height: Dp = 300.dp) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        AsyncImage(
            model = bannerUrl,
            contentDescription = "Banner",
            contentScale = ContentScale.Crop,
            fallback = ColorPainter(MaterialTheme.colorScheme.outline),
            placeholder = ColorPainter(MaterialTheme.colorScheme.outline),
            error = ColorPainter(MaterialTheme.colorScheme.outline),
            modifier = Modifier.fillMaxSize()
        )
        // gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x55000000),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        )
    }
}