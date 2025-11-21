package com.example.playground.mediaDetail.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.core.presentation.mapper.asWatchlistEntity
import com.example.playground.core.presentation.mapper.userMessage
import com.example.playground.core.utils.DataResult
import com.example.playground.mediaDetail.domain.model.MovieDetail
import com.example.playground.mediaDetail.domain.repository.MediaDetailRepository
import com.example.playground.watchlist.domain.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val mediaDetailRepo: MediaDetailRepository,
    private val watchlistRepo: WatchlistRepository
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getMovieDetail(movieId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isLoading = true,
                            errorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            movieDetail = result.data,
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

    fun addToWatchlist(movieDetail: MovieDetail) {
        viewModelScope.launch {
        watchlistRepo.insertMedia(movieDetail.asWatchlistEntity())
        }
    }

    fun removeFromWatchlist(id: Int) {
        viewModelScope.launch {
            watchlistRepo.removeMedia(mediaType = "movie", tmdbId = id)
        }
    }

    fun getWatchlistStatus(id: Int) {
        viewModelScope.launch {
            watchlistRepo.isMediaInWatchlist(mediaType = "movie", tmdbId = id).collect {
                _uiState.update { currentState -> currentState.copy(isInWatchlist = it) }
            }
        }
    }
}


