package com.elzren.mywatchlist.mediaDetail.domain.model.credit

import com.google.gson.annotations.SerializedName

data class Credit(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)
