package com.example.playground.core.presentation.mapper

import com.example.playground.core.domain.model.Media
import com.example.playground.core.domain.model.MediaModel
import com.example.playground.mediaDetail.domain.model.MovieDetail
import com.example.playground.mediaDetail.domain.model.ShowDetail
import com.example.playground.watchlist.data.local.WatchlistEntity

fun MediaModel.asMedia() = Media(
    id = id,
    mediaType = mediaType,
    title = title ?: name ?: "",
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    adult = adult,
    genreIds = genreIds,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    popularity = popularity,
    originalTitle = originalTitle ?: originalName ?: "",
    releaseDate = releaseDate ?: firstAirDate ?: "",
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun ShowDetail.asWatchlistEntity() = WatchlistEntity(
    tmdbId = id,
    mediaType = "tv",
    title = name,
    posterPath = posterPath,
    overview = overview,
    releaseDate = firstAirDate,
    originalLanguage = originalLanguage,
    voteAverage = voteAverage
)

fun MovieDetail.asWatchlistEntity() = WatchlistEntity(
    tmdbId = id,
    mediaType = "movie",
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    voteAverage = voteAverage
)