package com.example.yemekcalendar.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.core.presentation.components.Title
import com.example.yemekcalendar.core.presentation.components.YemekCalendarDropdownList
import com.example.yemekcalendar.settings.presentation.viewmodel.SettingsViewModel
import com.example.yemekcalendar.ui.theme.RegularFont
import com.example.yemekcalendar.ui.theme.YemekCalendarTheme

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    settingsViewModel.initialize()
    val settingsState by settingsViewModel.settingsState.collectAsStateWithLifecycle()

    val appearanceIcon = ImageVector.vectorResource(id = R.drawable.ic_appearance)
    val chevronIcon = ImageVector.vectorResource(id = R.drawable.outline_chevron_right_24)

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Title(
                text = stringResource(R.string.settings),
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .padding(
//                    top = 16.dp,
//                    start = 16.dp,
//                    end = 16.dp,
//                    bottom = 0.dp
//                )
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SettingsItem(
                    leadingIcon = Icons.Default.AccountCircle,
                    title = stringResource(R.string.profile),
                    subtitle = stringResource(R.string.profile_subtitle),
                    onClick = {
                        navController.navigate(Screen.ProfileScreen)
                    },
                    trailingContent = {
                        Icon(
                            imageVector = chevronIcon,
                            contentDescription = ""
                        )
                    }
                )
            }

            item {
                SettingsItem(
                    leadingIcon = appearanceIcon,
                    title = stringResource(R.string.appearance),
                    subtitle = stringResource(R.string.appearance_subtitle),
                    onClick = {
                        navController.navigate(Screen.AppearanceScreen)
                    },
                    trailingContent = {
                        Icon(imageVector = chevronIcon, contentDescription = "")
                    }
                )
            }

            item {
                SettingsItem(
                    leadingIcon = Icons.Default.DateRange,
                    title = stringResource(R.string.calendar_source),
                    subtitle = stringResource(R.string.calendar_source_subtitle),
                    onClick = {
                        navController.navigate(Screen.CalendarSourceScreen)
                    },
                    trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
                )
            }

            item {
                SettingsItem(
                    leadingIcon = Icons.Default.Notifications,
                    title = stringResource(R.string.notifications),
                    subtitle = stringResource(R.string.notifications_subtitle),
                    onClick = {
                        navController.navigate(Screen.NotificationsScreen)
                    },
                    trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
                )
            }

            item {
                SettingsItem(
                    leadingIcon = Icons.Default.Info,
                    title = stringResource(R.string.about),
                    subtitle = stringResource(R.string.about_subtitle),
                    onClick = {
                        navController.navigate(Screen.AboutScreen)
                    },
                    trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
                )
            }

        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "id:pixel_8_pro"
)
@Composable
private fun SettingsPreview() {
    YemekCalendarTheme {
        MockSettingsScreen(navController = NavController(LocalContext.current))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockSettingsScreen(
    navController: NavController,
) {
    var language by remember { mutableStateOf("English") }
    var theme by remember { mutableStateOf("Light") }
    val languages = listOf("English", "Spanish", "French")
    val themes = listOf("Light", "Dark")
    val languageIcon = ImageVector.vectorResource(id = R.drawable.outline_language_24)
    val chevronIcon = ImageVector.vectorResource(id = R.drawable.outline_chevron_right_24)
    val textColor = MaterialTheme.colorScheme.onSurface

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Title(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Settings",
                        textColor = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsItem(
                leadingIcon = Icons.Default.AccountCircle,
                title = "Profile",
                subtitle = "Account Info, Logout ..etc",
                onClick = { navController.navigate("profile") },
                trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
            )

            SettingsItem(
                leadingIcon = Icons.Default.DateRange,
                title = "Calendar Source",
                subtitle = "Firebase Database/Storage links",
                onClick = { navController.navigate("about") },
                trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
            )

            SettingsItem(
                leadingIcon = languageIcon,
                title = "Language",
                subtitle = "English or Turkish",
                trailingContent = {
                    YemekCalendarDropdownList(
                        modifier = Modifier
                            .width(100.dp)
                            .padding(vertical = 8.dp),
                        itemList = languages,
                        onSelectedIndexChanged = { language = it },
                        textColor = textColor
                    )
                }
            )

            SettingsItem(
                leadingIcon = Icons.Default.Menu,
                title = "Theme",
                subtitle = "Light, Dark or Default",
                trailingContent = {
                    YemekCalendarDropdownList(
                        modifier = Modifier.width(100.dp),
                        itemList = themes,
                        onSelectedIndexChanged = { theme = it },
                        textColor = textColor
                    )
                }
            )


            SettingsItem(
                leadingIcon = Icons.Default.Notifications,
                title = "Notifications & Sync",
                subtitle = "Notification Types, Sync Interval",
                onClick = { navController.navigate("about") },
                trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
            )


            SettingsItem(
                leadingIcon = Icons.Default.Info,
                title = "About",
                subtitle = "Author, Version, License ..etc",
                onClick = { navController.navigate("about") },
                trailingContent = { Icon(imageVector = chevronIcon, contentDescription = "") }
            )
        }
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {},
    trailingContent: @Composable () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.large
            )
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
            .sizeIn(minHeight = 100.dp),
        contentAlignment = Alignment.Center

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = RegularFont,
                    )

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = RegularFont,
                    )
                }
            }

            trailingContent()

        }
    }
}







