package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Heading(
    title: String,
    modifier: Modifier = Modifier,
    topPadding: Dp = 22.dp,
    bottomPadding: Dp = 12.dp,
) {
    Text(
        text = title,
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(top = topPadding, bottom = bottomPadding),
        style = MaterialTheme.typography.titleLarge
    )
}