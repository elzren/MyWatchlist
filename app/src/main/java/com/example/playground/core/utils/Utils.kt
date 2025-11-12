package com.example.playground.core.utils

fun String.toTmdbImgUrl(widthSize: String = "w500"): String = "${Constants.TMDB_IMAGE_BASE_URL}/${widthSize}${this}"