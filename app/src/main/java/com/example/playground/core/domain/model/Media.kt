package com.example.playground.core.domain.model

data class Media(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String,
    val originCountry: List<String>?,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val originalTitle: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
