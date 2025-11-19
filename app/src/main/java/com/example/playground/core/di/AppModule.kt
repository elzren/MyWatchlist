package com.example.playground.core.di

import com.example.playground.core.data.api.TmdbApiKeyInterceptor
import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.core.utils.Constants.TMDB_BASE_URL
import com.example.playground.home.data.repository.HomeRepositoryImp
import com.example.playground.home.domain.repository.HomeRepository
import com.example.playground.mediaDetail.data.repository.MediaDetailRepositoryImp
import com.example.playground.mediaDetail.domain.repository.MediaDetailRepository
import com.example.playground.search.data.repository.SearchRepositoryImp
import com.example.playground.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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