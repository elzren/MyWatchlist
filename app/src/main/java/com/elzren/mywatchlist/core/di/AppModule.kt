package com.elzren.mywatchlist.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.elzren.mywatchlist.core.data.api.TmdbApiKeyInterceptor
import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.data.repository.PreferencesRepositoryImp
import com.elzren.mywatchlist.core.domain.repository.PreferencesRepository
import com.elzren.mywatchlist.core.utils.Constants.DATABASE_NAME
import com.elzren.mywatchlist.core.utils.Constants.TMDB_BASE_URL
import com.elzren.mywatchlist.home.data.repository.HomeRepositoryImp
import com.elzren.mywatchlist.home.domain.repository.HomeRepository
import com.elzren.mywatchlist.media.data.repository.MediaRepositoryImp
import com.elzren.mywatchlist.media.domain.MediaRepository
import com.elzren.mywatchlist.mediaDetail.data.repository.MediaDetailRepositoryImp
import com.elzren.mywatchlist.mediaDetail.domain.repository.MediaDetailRepository
import com.elzren.mywatchlist.person.data.repository.PersonRepositoryImp
import com.elzren.mywatchlist.person.domain.repository.PersonRepository
import com.elzren.mywatchlist.search.data.repository.SearchRepositoryImp
import com.elzren.mywatchlist.search.domain.repository.SearchRepository
import com.elzren.mywatchlist.watchlist.data.local.WatchlistDao
import com.elzren.mywatchlist.watchlist.data.local.WatchlistDatabase
import com.elzren.mywatchlist.watchlist.data.repository.WatchlistRepositoryImp
import com.elzren.mywatchlist.watchlist.domain.repository.WatchlistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTmdbApiKeyInterceptor(): TmdbApiKeyInterceptor {
        return TmdbApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(tmdbApiKeyInterceptor: TmdbApiKeyInterceptor): TmdbApiService {
        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(tmdbApiKeyInterceptor).build())
            .build()
            .create(TmdbApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePreferencesDatastore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("preferences") }
    }

    @Provides
    @Singleton
    fun provideWatchlistDatabase(@ApplicationContext context: Context): WatchlistDatabase {
        return Room.databaseBuilder(
            context,
            WatchlistDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWatchlistDao(watchlistDatabase: WatchlistDatabase): WatchlistDao {
        return watchlistDatabase.getWatchlistDao()
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository =
        PreferencesRepositoryImp(dataStore)

    @Provides
    @Singleton
    fun provideWatchlistRepository(watchlistDao: WatchlistDao): WatchlistRepository =
        WatchlistRepositoryImp(watchlistDao)

    @Provides
    @Singleton
    fun provideHomeRepository(tmdbApi: TmdbApiService): HomeRepository = HomeRepositoryImp(tmdbApi)

    @Provides
    @Singleton
    fun provideMediaDetailRepository(tmdbApi: TmdbApiService): MediaDetailRepository =
        MediaDetailRepositoryImp(tmdbApi)

    @Provides
    @Singleton
    fun provideSearchRepository(
        tmdbApi: TmdbApiService,
        preferencesRepository: PreferencesRepository
    ): SearchRepository =
        SearchRepositoryImp(tmdbApi, preferencesRepository)

    @Provides
    @Singleton
    fun provideMediaRepository(tmdbApi: TmdbApiService): MediaRepository =
        MediaRepositoryImp(tmdbApi)

    @Provides
    @Singleton
    fun providePersonRepository(tmdbApi: TmdbApiService): PersonRepository =
        PersonRepositoryImp(tmdbApi)
}