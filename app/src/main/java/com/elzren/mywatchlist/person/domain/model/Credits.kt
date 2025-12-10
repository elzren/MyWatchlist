package com.elzren.mywatchlist.person.domain.model

data class Credits<T>(
    val cast: List<T>,
    val id: Int
)
