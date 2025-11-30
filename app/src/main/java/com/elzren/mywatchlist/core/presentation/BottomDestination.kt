package com.elzren.mywatchlist.core.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.presentation.navigation.Routes

enum class BottomDestination(
    val route: Any,
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int,
    @param:DrawableRes val iconSelected: Int
) {
    HOME(
        Routes.Home,
        R.string.home,
        R.drawable.home_24px,
        R.drawable.home_filled_24px
    ),
    SEARCH(
        Routes.Search,
        R.string.search,
        R.drawable.search_24px,
        R.drawable.search_24px
    ),
    WATCHLIST(
        Routes.Watchlist,
        R.string.watchlist,
        R.drawable.format_list_bulleted_24px,
        R.drawable.format_list_bulleted_24px
    ),
    SETTINGS(
        Routes.Settings,
        R.string.settings,
        R.drawable.settings_24px,
        R.drawable.settings_filled_24px
    ),
}