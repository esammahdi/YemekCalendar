package com.example.yemekcalendar.ui.theme

import android.content.Context
import com.example.yemekcalendar.R

enum class AppTheme(val titleRes: Int) {
    DEFAULT(R.string.label_default),
    DISABLED(R.string.theme_disabled),
    GREEN_APPLE(R.string.theme_greenapple),
    LAVENDER(R.string.theme_lavender),
    MIDNIGHT_DUSK(R.string.theme_midnightdusk),
    NORD(R.string.theme_nord),
    STRAWBERRY_DAIQUIRI(R.string.theme_strawberrydaiquiri),
    TAKO(R.string.theme_tako),
    TEALTURQUOISE(R.string.theme_tealturquoise),
    TIDAL_WAVE(R.string.theme_tidalwave),
    YOTSUBA(R.string.theme_yotsuba);

    fun getString(context: Context): String {
        return context.getString(titleRes)
    }

    companion object {
        fun fromString(context: Context, value: String): AppTheme {
            return entries.find { it.getString(context) == value } ?: DEFAULT
        }
    }
}