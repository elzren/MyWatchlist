package com.elzren.mywatchlist.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elzren.mywatchlist.core.presentation.mapper.userMessage
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTrendingMovies()
        getTrendingShows()
        getPopularMovies()
        getPopularShows()
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            homeRepository.getTrendingMoviesToday().collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isTrendingMoviesLoading = true,
                            errorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            trendingMovies = result.data,
                            isTrendingMoviesLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error.userMessage(),
                            isTrendingMoviesLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getTrendingShows() {
        viewModelScope.launch {
            homeRepository.getTrendingShowsToday().collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isTrendingShowsLoading = true,
                            errorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            trendingShows = result.data,
                            isTrendingShowsLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error.userMessage(),
                            isTrendingShowsLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            homeRepository.getPopularMovies().collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isPopularMoviesLoading = true,
                            errorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            popularMovies = result.data,
                            isPopularMoviesLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error.userMessage(),
                            isPopularMoviesLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getPopularShows() {
        viewModelScope.launch {
            homeRepository.getPopularShows().collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isPopularShowsLoading = true,
                            errorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            popularShows = result.data,
                            isPopularShowsLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error.userMessage(),
                            isPopularShowsLoading = false
                        )
                    }
                }
            }
        }
    }
}