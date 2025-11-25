package com.example.playground.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.playground.core.domain.model.Theme
import com.example.playground.core.domain.repository.PreferencesRepository
import com.example.playground.core.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImp @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesRepository {
    override fun getTheme(): Flow<Theme> = dataStore.data.map { preferences ->
        preferences[Constants.THEME_KEY] ?: Theme.SYSTEM.name
    }.map { Theme.valueOfOrNull(it) ?: Theme.SYSTEM }

    override suspend fun setTheme(theme: Theme) {
        dataStore.edit { preferences -> preferences[Constants.THEME_KEY] = theme.name }
    }
}