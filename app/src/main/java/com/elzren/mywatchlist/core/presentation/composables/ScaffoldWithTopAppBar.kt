package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopAppBar(
    title: String?,
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable (() -> Unit) = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable (() -> Unit) = {},
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    content: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (title != null) Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors
            )
        },
        floatingActionButton = floatingActionButton,
        contentWindowInsets = contentWindowInsets,
        modifier = modifier,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithMediumTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {},
    scrollBehavior: TopAppBarScrollBehavior,
    contentWindowInsets: WindowInsets = WindowInsets.systemBars,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 32.sp
                    )
                },
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = floatingActionButton,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}