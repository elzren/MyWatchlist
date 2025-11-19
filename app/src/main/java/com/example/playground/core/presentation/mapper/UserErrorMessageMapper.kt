package com.example.playground.core.presentation.mapper

import androidx.annotation.StringRes
import com.example.playground.R
import okio.IOException

@StringRes
fun Throwable.userMessage() = when (this) {
    is IOException -> R.string.no_internet_connection
    else -> R.string.unknown_error
}
