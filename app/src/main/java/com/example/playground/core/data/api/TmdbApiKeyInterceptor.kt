package com.example.playground.core.data.api

import com.example.playground.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TmdbApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        val newUrl = url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}