package com.example.playground.mediaDetail.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.core.presentation.mapper.userMessage
import com.example.playground.core.utils.DataResult
import com.example.playground.mediaDetail.domain.repository.MediaDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val mediaDetailRepo: MediaDetailRepository) :
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
}