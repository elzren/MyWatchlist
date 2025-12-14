package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elzren.mywatchlist.R

@Composable
fun Synopsis(synopsis: String, modifier: Modifier = Modifier) {
    var isSynopsisOverflowing by remember { mutableStateOf(false) }
    var isSynopsisExpanded by remember { mutableStateOf(false) }

    fun toggleSynopsisExpand() {
        isSynopsisExpanded = !isSynopsisExpanded
    }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            text = synopsis,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            maxLines = if (isSynopsisExpanded) Int.MAX_VALUE else 5,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result -> if (!isSynopsisOverflowing) isSynopsisOverflowing = result.hasVisualOverflow },
            modifier = Modifier
                .then(
                    if (isSynopsisOverflowing)
                        Modifier.clickable(onClick = { toggleSynopsisExpand() })
                    else Modifier
                )
                .animateContentSize()
        )

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            if (isSynopsisOverflowing) {
                if (isSynopsisExpanded) {
                    IconButton(onClick = { toggleSynopsisExpand() }) {
                        Icon(
                            painter = painterResource(R.drawable.keyboard_arrow_up_24px),
                            contentDescription = "Collapse"
                        )
                    }
                } else {
                    IconButton(onClick = { toggleSynopsisExpand() }) {
                        Icon(
                            painter = painterResource(R.drawable.keyboard_arrow_down_24px),
                            contentDescription = "Expand"
                        )
                    }
                }
            }
        }
    }
}

