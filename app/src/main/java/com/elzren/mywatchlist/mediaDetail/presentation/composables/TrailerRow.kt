package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.composables.HorizontalFeed
import com.elzren.mywatchlist.core.presentation.composables.Image
import com.elzren.mywatchlist.core.presentation.theme.overlay_color
import com.elzren.mywatchlist.core.utils.ContextUtils.openActionView
import com.elzren.mywatchlist.mediaDetail.domain.model.Video
import com.elzren.mywatchlist.mediaDetail.utils.Utils.getTrailerThumbnail
import com.elzren.mywatchlist.mediaDetail.utils.Utils.getTrailerUri

@Composable
fun TrailerRow(trailerList: List<Video>) {
    val context = LocalContext.current

    HorizontalFeed(items = trailerList, gap = 8.dp, itemContent = { trailer ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    width = TRAILER_WIDTH.dp,
                    height = TRAILER_HEIGHT.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = {
                    context.openActionView(getTrailerUri(trailer.key))
                })
        ) {
            Image(
                imageUrl = getTrailerThumbnail(trailer.key), modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlay_color)
            )
            Icon(
                painter = painterResource(R.drawable.play_arrow_40px),
                contentDescription = null,
                tint = Color.White
            )
        }
    })
}

const val TRAILER_WIDTH = 240
const val TRAILER_HEIGHT = 134