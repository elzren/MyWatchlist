package com.example.playground.core.domain.repository

import com.example.playground.core.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}