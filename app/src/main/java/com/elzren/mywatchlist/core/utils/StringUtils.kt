package com.elzren.mywatchlist.core.utils

object StringUtils {
    fun String.toTmdbImgUrl(widthSize: String = "w500"): String =
        "${Constants.TMDB_IMAGE_BASE_URL}/${widthSize}${this}"
}
