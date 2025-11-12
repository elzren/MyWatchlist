package com.example.playground.core.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable (() -> Unit) = {},
    contentWindowInsets: WindowInsets = WindowInsets.systemBars,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable (() -> Unit) = {},
    actions: @Composable (RowScope.() -> Unit) = {},
    content: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                scrollBehavior = scrollBehavior,
                navigationIcon = navigationIcon,
                actions = actions
            )
        },
        floatingActionButton = floatingActionButton,
        contentWindowInsets = contentWindowInsets,
        modifier = modifier,
        content = content
    )
}