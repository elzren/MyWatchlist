package com.elzren.mywatchlist.mediaDetail.domain.model.keyword

import com.google.gson.annotations.SerializedName

data class ShowKeywordResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val keywords: List<Keyword>
)
