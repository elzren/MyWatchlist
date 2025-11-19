package com.example.playground.core.presentation.mapper

import com.example.playground.core.domain.model.Media
import com.example.playground.core.domain.model.MediaModel

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