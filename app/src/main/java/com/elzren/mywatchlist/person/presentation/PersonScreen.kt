package com.elzren.mywatchlist.person.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.elzren.mywatchlist.core.domain.model.TabItem
import com.elzren.mywatchlist.core.presentation.composables.BackIconButton
import com.elzren.mywatchlist.core.presentation.composables.HorizontalPagerWithTabs
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.person.domain.model.PersonTab
import com.elzren.mywatchlist.person.presentation.composables.PersonMovies
import com.elzren.mywatchlist.person.presentation.composables.PersonOverview
import com.elzren.mywatchlist.person.presentation.composables.PersonProfile
import com.elzren.mywatchlist.person.presentation.composables.PersonShows

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonScreen(
    personId: Int,
    personProfilePath: String?,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val personUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = personId) {
        if (personId != personUiState.personId) {
            viewModel.setPersonId(personId)
            viewModel.getPersonDetail(personId)
            viewModel.getPersonMovies(personId)
            viewModel.getPersonShows(personId)
        }
    }

    val personDetail = personUiState.personDetail
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isTopAppBarCollapsed by remember {
        derivedStateOf { topAppBarScrollBehavior.state.collapsedFraction >= 0.9f }
    }
    val tabs = remember {
        PersonTab.entries.map { TabItem(value = it, title = it.string) }
    }

    ScaffoldWithTopAppBar(
        title = if (isTopAppBarCollapsed) personDetail?.name else null,
        navigationIcon = { BackIconButton(onClick = navActionManager::goBack) },
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
        modifier = modifier
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TopAppBar(
                windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
                title = {
                    PersonProfile(
                        personProfilePath = personProfilePath,
                        personDetail = personDetail
                    )
                },
                scrollBehavior = topAppBarScrollBehavior,
            )
            HorizontalPagerWithTabs(
                tabs = tabs,
            ) { page ->
                when (page) {
                    PersonTab.OVERVIEW.ordinal -> PersonOverview(
                        personUiState = personUiState,
                        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    )

                    PersonTab.MOVIES.ordinal -> PersonMovies(
                        personUiState = personUiState,
                        navActionManager = navActionManager,
                        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    )

                    PersonTab.SHOWS.ordinal -> PersonShows(
                        personUiState = personUiState,
                        navActionManager = navActionManager,
                        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    )
                }
            }
        }
    }
}