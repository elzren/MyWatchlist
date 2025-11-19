package com.example.playground.mediaDetail.presentation.show

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
class ShowDetailViewModel @Inject constructor(val mediaDetailRepo: MediaDetailRepository) :
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
}