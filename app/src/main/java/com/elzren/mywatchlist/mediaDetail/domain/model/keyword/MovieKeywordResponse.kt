package com.elzren.mywatchlist.mediaDetail.domain.model.keyword

import com.google.gson.annotations.SerializedName

data class MovieKeywordResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("keywords")
    val keywords: List<Keyword>
)
