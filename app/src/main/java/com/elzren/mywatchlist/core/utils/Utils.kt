package com.elzren.mywatchlist.core.utils

object Utils {
    fun Int.nonZeroOrNull() = if (this == 0) null else this
    fun Long.nonZeroOrNull() = if (this == 0L) null else this
    fun Double.nonZeroOrNull() = if (this == 0.0) null else this
}