package com.elzren.mywatchlist.mediaDetail.presentation.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elzren.mywatchlist.core.presentation.mapper.asWatchlistEntity
import com.elzren.mywatchlist.core.presentation.mapper.userMessage
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.mediaDetail.domain.model.ShowDetail
import com.elzren.mywatchlist.mediaDetail.domain.repository.MediaDetailRepository
import com.elzren.mywatchlist.watchlist.domain.repository.WatchlistRepository
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

    fun getShowData(showId: Int) {
        getShowDetail(showId)
        getWatchlistStatus(showId)
        getShowCast(showId)
        getShowRecommendations(showId)
        getShowKeywords(showId)
        getShowTrailer(showId)
    }

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

    fun getShowCast(showId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getShowCast(showId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isCastLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            showCast = result.data,
                            isCastLoading = false
                        )

                        is DataResult.Error -> currentState
                    }
                }
            }
        }
    }

    fun getShowRecommendations(showId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getShowRecommendations(showId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isRecommendationsLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            showRecommendations = result.data,
                            isRecommendationsLoading = false
                        )

                        is DataResult.Error -> currentState.copy(
                            isRecommendationsLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getShowKeywords(showId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getShowKeywords(showId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isKeywordsLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            showKeywords = result.data,
                            isKeywordsLoading = false
                        )

                        is DataResult.Error -> currentState.copy(
                            isKeywordsLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getShowTrailer(showId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getShowTrailer(showId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isTrailerLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            showTrailer = result.data,
                            isTrailerLoading = false
                        )

                        is DataResult.Error -> currentState.copy(
                            isTrailerLoading = false
                        )
                    }
                }
            }
        }
    }
}