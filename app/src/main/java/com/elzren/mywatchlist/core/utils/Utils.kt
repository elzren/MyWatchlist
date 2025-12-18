package com.elzren.mywatchlist.core.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import io.github.fornewid.placeholder.foundation.PlaceholderHighlight
import io.github.fornewid.placeholder.foundation.placeholder
import io.github.fornewid.placeholder.material3.fade

object Utils {
    fun Int.nonZeroOrNull() = if (this == 0) null else this
    fun Long.nonZeroOrNull() = if (this == 0L) null else this
    fun Double.nonZeroOrNull() = if (this == 0.0) null else this

    fun Modifier.defaultPlaceholder(visible: Boolean) = composed {
        placeholder(
            visible = visible,
            color = MaterialTheme.colorScheme.outline,
            highlight = PlaceholderHighlight.fade(),
            shape = MaterialTheme.shapes.small
        )
    }
}