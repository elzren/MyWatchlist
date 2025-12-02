package com.elzren.mywatchlist.watchlist.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elzren.mywatchlist.core.utils.Constants

@Entity(tableName = Constants.Tables.WATCHLIST)
data class WatchlistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "tmdb_id")
    val tmdbId: Int,

    @ColumnInfo(name = "media_type")
    val mediaType: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
)