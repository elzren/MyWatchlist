package com.example.playground.core.domain.model

enum class Theme {
    SYSTEM, LIGHT, DARK;

    companion object {
        fun valueOfOrNull(value: String) = try {
            valueOf(value)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}