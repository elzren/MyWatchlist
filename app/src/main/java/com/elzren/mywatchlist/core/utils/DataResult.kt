package com.elzren.mywatchlist.core.utils

sealed class DataResult<T> {
    class Success<T>(val data: T) : DataResult<T>()
    class Error<T>(val error: Throwable) : DataResult<T>()
    class Loading<T> : DataResult<T>()
}