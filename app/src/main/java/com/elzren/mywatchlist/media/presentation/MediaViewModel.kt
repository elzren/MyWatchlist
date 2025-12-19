package com.elzren.mywatchlist.media.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.elzren.mywatchlist.core.presentation.mapper.asMedia
import com.elzren.mywatchlist.media.domain.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(private val mediaRepository: MediaRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(MediaUiState())
    val uiState = _uiState.asStateFlow()

    fun getMedia() {
        viewModelScope.launch {
            val genres = uiState.value.genres
            val keywords = uiState.value.keywords

            val movies = mediaRepository.getMovies(genres, keywords)
                .map { pagingData ->
                    pagingData.map { movie -> movie.asMedia() }
                }
                .cachedIn(viewModelScope)

            val shows = mediaRepository.getShows(genres, keywords)
                .map { pagingData ->
                    pagingData.map { show -> show.asMedia() }
                }
                .cachedIn(viewModelScope)

            _uiState.update { currentState -> currentState.copy(movies = movies, shows = shows) }
        }
    }

    fun setGenresAndKeywords(genres: String?, keywords: String?) {
        _uiState.update { currentState -> currentState.copy(genres = genres, keywords = keywords) }
    }
}