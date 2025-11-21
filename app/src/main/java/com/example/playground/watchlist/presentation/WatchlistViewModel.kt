package com.example.playground.watchlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.core.presentation.mapper.userMessage
import com.example.playground.watchlist.domain.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(watchlistRepo: WatchlistRepository) :
    ViewModel() {

    val uiState: StateFlow<WatchlistUiState> = watchlistRepo.getWatchlist()
        .map { watchlist ->
            WatchlistUiState(watchlistItems = watchlist, isLoading = false)
        }
        .catch { exception ->
            WatchlistUiState(
                errorMessage = exception.userMessage(),
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WatchlistUiState(isLoading = true)
        )
}