package com.example.playground.core.di

import android.content.Context
import androidx.room.Room
import com.example.playground.core.data.api.TmdbApiKeyInterceptor
import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.core.utils.Constants.DATABASE_NAME
import com.example.playground.core.utils.Constants.TMDB_BASE_URL
import com.example.playground.home.data.repository.HomeRepositoryImp
import com.example.playground.home.domain.repository.HomeRepository
import com.example.playground.mediaDetail.data.repository.MediaDetailRepositoryImp
import com.example.playground.mediaDetail.domain.repository.MediaDetailRepository
import com.example.playground.search.data.repository.SearchRepositoryImp
import com.example.playground.search.domain.repository.SearchRepository
import com.example.playground.watchlist.data.local.WatchlistDao
import com.example.playground.watchlist.data.local.WatchlistDatabase
import com.example.playground.watchlist.data.repository.WatchlistRepositoryImp
import com.example.playground.watchlist.domain.repository.WatchlistRepository
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
    fun provideWatchlistDatabase(@ApplicationContext context: Context): WatchlistDatabase {
        return Room.databaseBuilder(
            context,
            WatchlistDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWatchlistDao(watchlistDatabase: WatchlistDatabase): WatchlistDao {
        return watchlistDatabase.getWatchlistDao()
    }

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
    fun provideSearchRepository(tmdbApi: TmdbApiService): SearchRepository =
        SearchRepositoryImp(tmdbApi)
}