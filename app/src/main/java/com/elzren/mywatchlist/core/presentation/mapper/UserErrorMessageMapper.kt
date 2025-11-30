package com.elzren.mywatchlist.core.presentation.mapper

import androidx.annotation.StringRes
import com.elzren.mywatchlist.R
import okio.IOException

@StringRes
fun Throwable.userMessage() = when (this) {
    is IOException -> R.string.no_internet_connection
    else -> R.string.unknown_error
}
