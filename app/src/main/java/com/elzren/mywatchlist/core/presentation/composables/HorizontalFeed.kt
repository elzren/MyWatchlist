package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> HorizontalFeed(
    items: List<T>,
    modifier: Modifier = Modifier,
    gap: Dp = 4.dp,
    itemContent: @Composable (LazyItemScope.(T) -> Unit),
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal =  16.dp),
        horizontalArrangement = Arrangement.spacedBy(gap),
        modifier = modifier
    ) {
        items(items = items, itemContent = itemContent)
    }
}