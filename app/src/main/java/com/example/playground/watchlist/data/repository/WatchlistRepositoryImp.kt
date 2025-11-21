package com.example.playground.watchlist.data.repository

import com.example.playground.watchlist.data.local.WatchlistDao
import com.example.playground.watchlist.data.local.WatchlistEntity
import com.example.playground.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistRepositoryImp @Inject constructor(private val watchlistDao: WatchlistDao) :
    WatchlistRepository {
    override fun isMediaInWatchlist(
        mediaType: String,
        tmdbId: Int
    ) = watchlistDao.isMediaInWatchlist(mediaType, tmdbId)

    override suspend fun insertMedia(media: WatchlistEntity) = watchlistDao.insertMedia(media)

    override suspend fun removeMedia(
        mediaType: String,
        tmdbId: Int
    ) = watchlistDao.removeMedia(mediaType, tmdbId)

    override fun getWatchlist(): Flow<List<WatchlistEntity>> = watchlistDao.getWatchlist()
}