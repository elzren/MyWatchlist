package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elzren.mywatchlist.core.presentation.composables.MediaPoster
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.StringUtils.toTmdbImgUrl
import com.elzren.mywatchlist.core.utils.Utils.defaultPlaceholder

const val CAST_ITEM_WIDTH = 125
const val CAST_ITEM_HEIGHT = 187.5

@Composable
fun CastItem(
    id: Int,
    profilePath: String?,
    characterName: String,
    playedBy: String,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clickable(onClick = {
        navActionManager.toPersonScreen(
            id,
            profilePath
        )
    }), contentAlignment = Alignment.BottomStart) {
        MediaPoster(
            posterUrl = profilePath?.toTmdbImgUrl(),
            width = CAST_ITEM_WIDTH.dp,
            height = CAST_ITEM_HEIGHT.dp
        )
        Box(
            modifier = Modifier
                .width(CAST_ITEM_WIDTH.dp)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = characterName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp
                )
                Text(
                    text = playedBy,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }
}

@Composable
fun CastItemPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .defaultPlaceholder(visible = true)
            .size(width = CAST_ITEM_WIDTH.dp, height = CAST_ITEM_HEIGHT.dp)
    )
}