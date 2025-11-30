package com.elzren.mywatchlist.core.domain.repository

import com.elzren.mywatchlist.core.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}