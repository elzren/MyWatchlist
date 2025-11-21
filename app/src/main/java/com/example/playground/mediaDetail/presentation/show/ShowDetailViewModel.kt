package com.example.playground.mediaDetail.presentation.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.core.presentation.mapper.asWatchlistEntity
import com.example.playground.core.presentation.mapper.userMessage
import com.example.playground.core.utils.DataResult
import com.example.playground.mediaDetail.domain.model.ShowDetail
import com.example.playground.mediaDetail.domain.repository.MediaDetailRepository
import com.example.playground.watchlist.domain.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val mediaDetailRepo: MediaDetailRepository,
    private val watchlistRepo: WatchlistRepository
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ShowDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getShowDetail(showId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getShowDetail(showId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isLoading = true,
                            errorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            showDetail = result.data,
                            isLoading = false
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error.userMessage(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun addToWatchlist(showDetail: ShowDetail) {
        viewModelScope.launch {
            watchlistRepo.insertMedia(showDetail.asWatchlistEntity())
        }
    }

    fun removeFromWatchlist(id: Int) {
        viewModelScope.launch {
            watchlistRepo.removeMedia(mediaType = "tv", tmdbId = id)
        }
    }

    fun getWatchlistStatus(id: Int) {
        viewModelScope.launch {
           watchlistRepo.isMediaInWatchlist(mediaType = "tv", tmdbId = id).collect {
               _uiState.update { currentState -> currentState.copy(isInWatchlist = it) }
           }
        }
    }
}