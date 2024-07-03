package com.esammahdi.yemekcalendar.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.core.presentation.components.ComingSoonScreen


@Composable
fun CalendarSourceScreen(
    navController: NavController,
) {
    ComingSoonScreen(
        navController = navController,
        name = stringResource(id = R.string.calendar_source)
    )
}