package com.elzren.mywatchlist.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.elzren.mywatchlist.core.domain.model.Theme
import com.elzren.mywatchlist.core.domain.repository.PreferencesRepository
import com.elzren.mywatchlist.core.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    override fun getNsfw(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[Constants.NSFW_KEY] ?: false
    }

    override suspend fun getNsfwFirst(): Boolean = getNsfw().first()

    override suspend fun setNsfw(value: Boolean) {
        dataStore.edit { preferences -> preferences[Constants.NSFW_KEY] = value }
    }
}