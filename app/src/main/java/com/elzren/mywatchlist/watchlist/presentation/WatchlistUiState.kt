package com.elzren.mywatchlist.watchlist.presentation

import androidx.annotation.StringRes
import com.elzren.mywatchlist.watchlist.data.local.WatchlistEntity

data class WatchlistUiState(
    val watchlistItems: List<WatchlistEntity> = emptyList(),
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
)
