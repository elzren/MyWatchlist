package com.example.playground.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.playground.core.presentation.mapper.asMedia
import com.example.playground.search.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        _uiState.update { currentState -> currentState.copy(query = query) }
        if (query.isNotBlank()) {
            debouncedSearch(query.trim())
        }
    }

    fun resetQuery() {
        _uiState.update { currentState -> currentState.copy(query = "") }
    }

    private fun debouncedSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchMedia(query)
        }
    }

    private fun searchMedia(query: String) {
        val searchResults =
            searchRepository.getMediaSearchResults(query)
                .map { pagingData ->
                    pagingData
                        .filter { mediaModel ->
                            mediaModel.mediaType == "movie" || mediaModel.mediaType == "tv"
                        }
                        .map { mediaModel -> mediaModel.asMedia() }
                }
                .cachedIn(viewModelScope)

        _uiState.update { currentState -> currentState.copy(searchResults = searchResults) }
    }
}