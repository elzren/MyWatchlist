package com.example.playground.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenteredBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier,
        content = content
    )
}