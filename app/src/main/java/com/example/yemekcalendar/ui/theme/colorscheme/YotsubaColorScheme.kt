package com.example.yemekcalendar.ui.theme.colorscheme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Colors for Yotsuba theme
 * Original color scheme by ztimms73
 * M3 colors generated by Material Theme Builder (https://goo.gle/material-theme-builder-web)
 *
 * Key colors:
 * Primary 0xFFAE3200
 * Secondary 0xFFAE3200
 * Tertiary 0xFF6B5E2F
 * Neutral 0xFF655C5A
 */
internal object YotsubaColorScheme : BaseColorScheme() {

    override val darkScheme = darkColorScheme(
        primary = Color(0xFFFFB59D),
        onPrimary = Color(0xFF5F1600),
        primaryContainer = Color(0xFF862200),
        onPrimaryContainer = Color(0xFFFFDBCF),
        inversePrimary = Color(0xFFAE3200),
        secondary = Color(0xFFFFB59D),
        onSecondary = Color(0xFF5F1600),
        secondaryContainer = Color(0xFF862200),
        onSecondaryContainer = Color(0xFFFFDBCF),
        tertiary = Color(0xFFD7C68D),
        onTertiary = Color(0xFF3A2F05),
        tertiaryContainer = Color(0xFF524619),
        onTertiaryContainer = Color(0xFFF5E2A7),
        background = Color(0xFF211A18),
        onBackground = Color(0xFFEDE0DD),
        surface = Color(0xFF211A18),
        onSurface = Color(0xFFEDE0DD),
        surfaceVariant = Color(0xFF53433F),
        onSurfaceVariant = Color(0xFFD8C2BC),
        surfaceTint = Color(0xFFFFB59D),
        inverseSurface = Color(0xFFEDE0DD),
        inverseOnSurface = Color(0xFF211A18),
        outline = Color(0xFFA08C87),
    )

    override val lightScheme = lightColorScheme(
        primary = Color(0xFFAE3200),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFFFDBCF),
        onPrimaryContainer = Color(0xFF3B0A00),
        inversePrimary = Color(0xFFFFB59D),
        secondary = Color(0xFFAE3200),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFFFDBCF),
        onSecondaryContainer = Color(0xFF3B0A00),
        tertiary = Color(0xFF6B5E2F),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFF5E2A7),
        onTertiaryContainer = Color(0xFF231B00),
        background = Color(0xFFFCFCFC),
        onBackground = Color(0xFF211A18),
        surface = Color(0xFFFCFCFC),
        onSurface = Color(0xFF211A18),
        surfaceVariant = Color(0xFFF5DED8),
        onSurfaceVariant = Color(0xFF53433F),
        surfaceTint = Color(0xFFAE3200),
        inverseSurface = Color(0xFF362F2D),
        inverseOnSurface = Color(0xFFFBEEEB),
        outline = Color(0xFF85736E),
    )
}
