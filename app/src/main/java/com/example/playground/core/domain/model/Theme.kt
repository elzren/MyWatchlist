package com.example.playground.core.domain.model

import androidx.annotation.StringRes
import com.example.playground.R

enum class Theme(@param:StringRes val string: Int) {
    SYSTEM(R.string.theme_system),
    LIGHT(R.string.theme_light),
    DARK(R.string.theme_dark);

    companion object {
        fun valueOfOrNull(value: String) = try {
            valueOf(value)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}