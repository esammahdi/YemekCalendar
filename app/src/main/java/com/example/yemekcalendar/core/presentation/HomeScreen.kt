package com.example.yemekcalendar.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.calendar.presentation.CalendarScreen
import com.example.yemekcalendar.foodItemList.presentation.FoodListScreen
import com.example.yemekcalendar.settings.presentation.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {
    var selectedScreen by rememberSaveable { mutableStateOf("calendar") }

    Scaffold(
        modifier = Modifier.padding(bottom = 0.dp),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 0.dp)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Outlined.DateRange,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(id = R.string.calendar)) },
                    selected = selectedScreen.equals("calendar"),
                    onClick = {
                        selectedScreen = "calendar"
//                        navController.navigate(Screens.CalendarScreen.route)
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_nutrition_24),
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(id = R.string.list)) },
                    selected = selectedScreen.equals("list"),
                    onClick = {
                        selectedScreen = "list"
//                        navController.navigate(Screens.FoodItemListScreen.route)
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(id = R.string.settings)) },
                    selected = selectedScreen.equals("settings"),
                    onClick = {
                        selectedScreen = "settings"
//                        navController.navigate(Screens.SettingsScreen.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                "calendar" -> CalendarScreen(
                )

                "list" -> FoodListScreen(
                    navController = navController,
                )

                "settings" -> SettingsScreen(
                    navController = navController,
                )
            }
        }
    }
}