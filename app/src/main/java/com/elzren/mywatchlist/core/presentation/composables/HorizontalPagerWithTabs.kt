package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.elzren.mywatchlist.core.domain.model.TabItem
import kotlinx.coroutines.launch

@Composable
fun <T> HorizontalPagerWithTabs(
    modifier: Modifier = Modifier,
    tabs: List<TabItem<T>>,
    initialPage: Int,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    val pagerState =
        rememberPagerState(initialPage) { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    val tabsLayout = @Composable {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = if (tab.title != null) {
                    { Text(text = stringResource(tab.title)) }
                } else null,
                icon = if (tab.icon != null) {
                    {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = null
                        )
                    }
                } else null,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Column(modifier = modifier) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            tabs = tabsLayout
        )
        HorizontalPager(state = pagerState, pageContent = pageContent)
    }
}