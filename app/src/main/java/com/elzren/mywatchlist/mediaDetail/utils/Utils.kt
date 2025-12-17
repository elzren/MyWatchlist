package com.elzren.mywatchlist.mediaDetail.utils

import androidx.core.net.toUri
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE_IMAGE_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_IMAGE_QUALITY_PATH = "/hqdefault.jpg"

    const val BILLION = 1_000_000_000.0
    const val MILLION = 1_000_000.0
    const val THOUSAND = 1_000.0

    fun getTrailerUri(key: String) =
        (YOUTUBE_VIDEO_URL + key).toUri()

    fun getTrailerThumbnail(key: String) =
        YOUTUBE_IMAGE_URL + key + YOUTUBE_IMAGE_QUALITY_PATH

    fun getLanguageName(languageCode: String): String {
        val locale = Locale.forLanguageTag(languageCode)
        return locale.displayLanguage
    }

    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return date?.let { outputFormat.format(it) } ?: dateString
    }

    fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val minutes = minutes % 60

        return when {
            hours > 0 && minutes > 0 -> "$hours h $minutes min"
            hours > 0 -> "$hours h"
            else -> "$minutes min"
        }
    }

    fun formatDecimal(number: Double, pattern: String = "#,###.##"): String {
        val formatter = DecimalFormat(pattern)
        return formatter.format(number)
    }

    fun formatAmount(amount: Long): String {
        return when {
            amount >= BILLION -> formatDecimal(amount / BILLION) + " B"
            amount >= MILLION -> formatDecimal(amount / MILLION) + " M"
            else -> formatDecimal(amount / THOUSAND) + " K"
        }
    }

}