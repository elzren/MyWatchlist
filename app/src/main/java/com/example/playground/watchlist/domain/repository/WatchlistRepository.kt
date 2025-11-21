package com.example.playground.watchlist.domain.repository

import com.example.playground.watchlist.data.local.WatchlistEntity
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun isMediaInWatchlist(mediaType: String, tmdbId: Int): Flow<Boolean>
    suspend fun insertMedia(media: WatchlistEntity)
    suspend fun removeMedia(mediaType: String, tmdbId: Int)
    fun getWatchlist(): Flow<List<WatchlistEntity>>
}