package com.elzren.mywatchlist.search.domain.model

import androidx.annotation.DrawableRes


data class SearchItem(
    val name: String,
    val id: String,
    @param:DrawableRes val  icon: Int
)
