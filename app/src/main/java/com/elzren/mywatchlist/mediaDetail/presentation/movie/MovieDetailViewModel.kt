package com.elzren.mywatchlist.mediaDetail.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elzren.mywatchlist.core.presentation.mapper.asWatchlistEntity
import com.elzren.mywatchlist.core.presentation.mapper.userMessage
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.mediaDetail.domain.model.MovieDetail
import com.elzren.mywatchlist.mediaDetail.domain.repository.MediaDetailRepository
import com.elzren.mywatchlist.watchlist.domain.repository.WatchlistRepository
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

    fun getMovieData(movieId: Int) {
        getMovieDetail(movieId)
        getWatchlistStatus(movieId)
        getMovieCast(movieId)
        getMovieRecommendations(movieId)
        getMovieKeywords(movieId)
        getMovieTrailer(movieId)
    }

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

    fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getMovieCast(movieId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isCastLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            movieCast = result.data,
                            isCastLoading = false
                        )

                        is DataResult.Error -> currentState
                    }
                }
            }
        }
    }

    fun getMovieRecommendations(movieId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getMovieRecommendations(movieId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isRecommendationsLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            movieRecommendations = result.data,
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

    fun getMovieKeywords(movieId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getMovieKeywords(movieId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isKeywordsLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            movieKeywords = result.data,
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

    fun getMovieTrailer(movieId: Int) {
        viewModelScope.launch {
            mediaDetailRepo.getMovieTrailer(movieId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isTrailerLoading = true,
                        )

                        is DataResult.Success -> currentState.copy(
                            movieTrailer = result.data,
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


