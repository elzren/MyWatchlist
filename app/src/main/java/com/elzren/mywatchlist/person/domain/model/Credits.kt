package com.elzren.mywatchlist.person.domain.model

import com.google.gson.annotations.SerializedName

data class Credits<T>(
    @SerializedName("cast")
    val cast: List<T>,
    @SerializedName("id")
    val id: Int
)
