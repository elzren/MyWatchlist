package com.elzren.mywatchlist.core.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TabItem<T>(
    val value: T,
    @param:StringRes val title: Int? = null,
    @param:DrawableRes val icon: Int? = null
)
