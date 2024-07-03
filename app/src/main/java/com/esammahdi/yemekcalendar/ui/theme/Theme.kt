package com.esammahdi.yemekcalendar.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.DisabledColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.GreenAppleColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.LavenderColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.MidnightDuskColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.NordColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.StrawberryColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.TakoColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.TealTurqoiseColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.TidalWaveColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.YemekCalendarColorScheme
import com.esammahdi.yemekcalendar.ui.theme.colorscheme.YotsubaColorScheme

@Composable
fun YemekCalendarTheme(
    appTheme: AppTheme = AppTheme.DEFAULT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> getThemeColorScheme(
            appTheme = appTheme,
            isDarkTheme = darkTheme
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
@ReadOnlyComposable
fun getThemeColorScheme(
    appTheme: AppTheme,
    isDarkTheme: Boolean = isSystemInDarkTheme()
): ColorScheme {
    val colorScheme = when (appTheme) {
        AppTheme.DEFAULT -> YemekCalendarColorScheme
        AppTheme.DISABLED -> DisabledColorScheme
        AppTheme.GREEN_APPLE -> GreenAppleColorScheme
        AppTheme.LAVENDER -> LavenderColorScheme
        AppTheme.MIDNIGHT_DUSK -> MidnightDuskColorScheme
        AppTheme.NORD -> NordColorScheme
        AppTheme.STRAWBERRY_DAIQUIRI -> StrawberryColorScheme
        AppTheme.TAKO -> TakoColorScheme
        AppTheme.TEALTURQUOISE -> TealTurqoiseColorScheme
        AppTheme.TIDAL_WAVE -> TidalWaveColorScheme
        AppTheme.YOTSUBA -> YotsubaColorScheme
    }
    return colorScheme.getColorScheme(
        isDarkTheme
    )
}