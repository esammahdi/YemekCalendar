package com.example.yemekcalendar.ui.theme.colorscheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal object DisabledColorScheme : BaseColorScheme() {

    override val darkScheme: ColorScheme = darkColorScheme(
        primary = Color.Gray,
        onPrimary = Color.LightGray,
        primaryContainer = Color.DarkGray,
        onPrimaryContainer = Color.Gray,
        inversePrimary = Color.LightGray,
        secondary = Color.Gray,
        onSecondary = Color.LightGray,
        secondaryContainer = Color.DarkGray,
        onSecondaryContainer = Color.Gray,
        tertiary = Color.Gray,
        onTertiary = Color.LightGray,
        tertiaryContainer = Color.DarkGray,
        onTertiaryContainer = Color.Gray,
        background = Color.Black,
        onBackground = Color.Gray,
        surface = Color.Black,
        onSurface = Color.Gray,
        surfaceVariant = Color.DarkGray,
        onSurfaceVariant = Color.LightGray,
        surfaceTint = Color.Gray,
        inverseSurface = Color.LightGray,
        inverseOnSurface = Color.Black,
        outline = Color.Gray
    )

    override val lightScheme: ColorScheme = lightColorScheme(
        primary = Color.LightGray,
        onPrimary = Color.White,
        primaryContainer = Color.Gray,
        onPrimaryContainer = Color.DarkGray,
        inversePrimary = Color.Gray,
        secondary = Color.LightGray,
        onSecondary = Color.White,
        secondaryContainer = Color.Gray,
        onSecondaryContainer = Color.DarkGray,
        tertiary = Color.LightGray,
        onTertiary = Color.White,
        tertiaryContainer = Color.Gray,
        onTertiaryContainer = Color.DarkGray,
        background = Color.White,
        onBackground = Color.Gray,
        surface = Color.White,
        onSurface = Color.Gray,
        surfaceVariant = Color.LightGray,
        onSurfaceVariant = Color.DarkGray,
        surfaceTint = Color.Gray,
        inverseSurface = Color.DarkGray,
        inverseOnSurface = Color.LightGray,
        outline = Color.Gray
    )
}
