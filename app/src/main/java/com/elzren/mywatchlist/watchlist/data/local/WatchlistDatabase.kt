package com.elzren.mywatchlist.watchlist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 2, entities = [WatchlistEntity::class], exportSchema = false)
abstract class WatchlistDatabase : RoomDatabase() {
    abstract fun getWatchlistDao(): WatchlistDao
}