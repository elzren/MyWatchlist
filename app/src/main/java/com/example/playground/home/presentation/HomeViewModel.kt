package com.example.playground.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.core.utils.DataResult
import com.example.playground.home.domain.repository.HomeRepository
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
                            trendingMovies = result.data ?: emptyList(),
                            isTrendingMoviesLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error,
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
                            trendingShows = result.data ?: emptyList(),
                            isTrendingShowsLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            errorMessage = result.error,
                            isTrendingShowsLoading = false
                        )
                    }
                }
            }
        }
    }
}