package com.example.yemekcalendar.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.presentation.components.ComingSoonScreen


@Composable
fun NotificationsScreen(
    navController: NavController,
) {
    ComingSoonScreen(
        navController = navController,
        name = stringResource(id = R.string.notifications)
    )
}