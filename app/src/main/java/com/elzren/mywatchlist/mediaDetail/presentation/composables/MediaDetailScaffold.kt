package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.core.presentation.composables.BackIconButton
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailScaffold(
    title: String,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues) -> Unit)
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isTopAppBarScrolled by remember {
        derivedStateOf { topAppBarScrollBehavior.state.overlappedFraction == 1f }
    }

    ScaffoldWithTopAppBar(
        title = if (isTopAppBarScrolled) title else null,
        navigationIcon = { BackIconButton(onClick = navActionManager::goBack) },
        scrollBehavior = topAppBarScrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        ),
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        content = content
    )
}