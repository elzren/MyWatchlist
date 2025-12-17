package com.elzren.mywatchlist.mediaDetail.utils

import androidx.core.net.toUri

object Utils {
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE_IMAGE_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_IMAGE_QUALITY_PATH = "/hqdefault.jpg"

    fun getTrailerUri(key: String) =
        (YOUTUBE_VIDEO_URL + key).toUri()

    fun getTrailerThumbnail(key: String) =
        YOUTUBE_IMAGE_URL  + key + YOUTUBE_IMAGE_QUALITY_PATH
}