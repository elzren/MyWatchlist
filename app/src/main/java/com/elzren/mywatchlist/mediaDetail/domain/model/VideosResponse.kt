package com.elzren.mywatchlist.mediaDetail.domain.model

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Video>
)
