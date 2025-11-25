package com.example.playground.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.core.domain.model.Theme
import com.example.playground.core.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    val theme = preferencesRepository.getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = Theme.SYSTEM
    )

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            preferencesRepository.setTheme(theme)
        }
    }
}