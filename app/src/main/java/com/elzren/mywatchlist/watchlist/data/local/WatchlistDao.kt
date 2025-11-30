package com.elzren.mywatchlist.watchlist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elzren.mywatchlist.core.utils.Constants.Tables.WATCHLIST
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT EXISTS(SELECT * FROM $WATCHLIST WHERE media_type IS :mediaType AND tmdb_id IS :tmdbId)")
    fun isMediaInWatchlist(mediaType: String, tmdbId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMedia(media: WatchlistEntity)

    @Query("DELETE FROM $WATCHLIST WHERE media_type IS :mediaType AND tmdb_id IS :tmdbId")
    suspend fun removeMedia(mediaType: String, tmdbId: Int)

    @Query("SELECT * FROM $WATCHLIST ORDER BY id DESC")
    fun getWatchlist(): Flow<List<WatchlistEntity>>
}