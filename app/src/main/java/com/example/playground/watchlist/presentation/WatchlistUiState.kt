package com.example.playground.watchlist.presentation

import androidx.annotation.StringRes
import com.example.playground.watchlist.data.local.WatchlistEntity

data class WatchlistUiState(
    val watchlistItems: List<WatchlistEntity> = emptyList(),
    val isLoading: Boolean = false,
    @param:StringRes val errorMessage: Int? = null,
)
