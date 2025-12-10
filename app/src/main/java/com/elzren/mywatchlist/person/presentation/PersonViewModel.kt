package com.elzren.mywatchlist.person.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elzren.mywatchlist.core.presentation.mapper.userMessage
import com.elzren.mywatchlist.core.utils.DataResult
import com.elzren.mywatchlist.person.domain.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(PersonUiState())
    val uiState = _uiState.asStateFlow()

    fun getPersonDetail(personId: Int) {
        viewModelScope.launch {
            personRepository.getPersonDetail(personId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isPersonDetailLoading = true,
                            detailErrorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            personDetail = result.data,
                            isPersonDetailLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            detailErrorMessage = result.error.userMessage(),
                            isPersonDetailLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getPersonMovies(personId: Int) {
        viewModelScope.launch {
            personRepository.getPersonMovies(personId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isPersonMoviesLoading = true,
                            moviesErrorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            personMovies = result.data,
                            isPersonMoviesLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            moviesErrorMessage = result.error.userMessage(),
                            isPersonMoviesLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getPersonShows(personId: Int) {
        viewModelScope.launch {
            personRepository.getPersonShows(personId).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is DataResult.Loading -> currentState.copy(
                            isPersonShowsLoading = true,
                            showsErrorMessage = null
                        )

                        is DataResult.Success -> currentState.copy(
                            personShows = result.data,
                            isPersonShowsLoading = false,
                        )

                        is DataResult.Error -> currentState.copy(
                            showsErrorMessage = result.error.userMessage(),
                            isPersonShowsLoading = false
                        )
                    }
                }
            }
        }
    }

    fun setPersonId(personId: Int) {
        _uiState.update { currentState -> currentState.copy(personId = personId) }
    }
}