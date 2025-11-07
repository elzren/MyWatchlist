package com.example.playground.core.domain.model

import com.google.gson.annotations.SerializedName

data class MediaResponse<T>(
    @SerializedName("results")
    val results: List<T>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
