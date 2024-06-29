package com.example.yemekcalendar.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.settings.presentation.viewmodel.AppearanceViewModel
import com.example.yemekcalendar.settings.presentation.viewmodel.Languages
import com.example.yemekcalendar.settings.presentation.viewmodel.ThemeMode
import com.example.yemekcalendar.ui.theme.AppTheme
import com.example.yemekcalendar.ui.theme.LabelTextStyle
import com.example.yemekcalendar.ui.theme.YemekCalendarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    navController: NavController,
    appearanceViewModel: AppearanceViewModel = hiltViewModel(),
) {
    appearanceViewModel.initialize()
    val appearanceState by appearanceViewModel.appearanceState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    val isDisabled = appearanceState.isDynamicColor

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.appearance),
                        style = LabelTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start,
            ) {

                Text(
                    text = stringResource(id = R.string.theme),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            MultiChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                ThemeMode.entries.onEachIndexed { index, themeMode ->
                    SegmentedButton(
                        checked = appearanceState.selectedThemeMode == themeMode,
                        onCheckedChange = {
                            appearanceViewModel.onThemeModeSelected(themeMode)
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index,
                            ThemeMode.entries.size,
                        ),
                    ) {
                        Text(
                            text = stringResource(themeMode.themeResId),
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row {
                    Text(
                        text = stringResource(id = R.string.dynamic_color),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { showDialog = true },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Checkbox(
                    checked = appearanceState.isDynamicColor,
                    onCheckedChange = {
                        appearanceViewModel.onDynamicColorSelected(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theme Cards
            LazyRow(
                modifier = Modifier
                    .let {
                        if (isDisabled) {
                            it.background(Color.Gray.copy(alpha = 0.5f))
                        } else {
                            it
                        }
                    }
                    .padding(horizontal = 8.dp),
                userScrollEnabled = !isDisabled,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    AppTheme.entries,
                    key = { it.name }
                ) { appTheme ->
                    Column(
                        modifier = Modifier
                            .width(114.dp)
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        YemekCalendarTheme(
                            appTheme = if (isDisabled) AppTheme.DISABLED else appTheme,
                            dynamicColor = if (isDisabled) false else appearanceState.isDynamicColor,
                            darkTheme = appearanceState.selectedThemeMode == ThemeMode.DARK || (appearanceState.selectedThemeMode == ThemeMode.SYSTEM && isSystemInDarkTheme()),
                        ) {
                            AppThemePreviewItem(
                                isSelected = if (isDisabled) false else appearanceState.selectedTheme == appTheme,
                                clickable = !isDisabled,
                                onClick = {
                                    appearanceViewModel.onThemeSelected(appTheme)
                                },
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = appTheme.titleRes),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = DividerDefaults.color,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = stringResource(id = R.string.language),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            //Language Choice
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                items(Languages.entries) {
                    ListItem(
                        modifier = Modifier.clickable {
                            appearanceViewModel.onLanguageSelected(it)
                        },
                        headlineContent = { Text(text = it.name) },
                        supportingContent = {
                            Text(
                                text = stringResource(id = it.languageStringResourceId),
                            )
                        },
                        trailingContent = {
                            if (
                                appearanceState.selectedLanguage == it
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = stringResource(androidx.compose.ui.R.string.selected),
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        },
                    )
                }
            }
        }
    }

    if (showDialog) {
        val dialogText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(id = R.string.dynamic_color) + ": ")
            }
            append(stringResource(id = R.string.dynamic_color_description))
        }

        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.dynamic_color)
                )
            },
            text = {
                Text(
                    text = dialogText
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

}


@Composable
fun AppThemePreviewItem(
    isSelected: Boolean,
    clickable: Boolean = true,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .border(
                width = 4.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    DividerDefaults.color
                },
                shape = RoundedCornerShape(17.dp),
            )
            .padding(4.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                enabled = clickable,
                onClick = onClick
            ),
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(0.7f)
                    .padding(end = 4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = MaterialTheme.shapes.small,
                    ),
            )

            Box(
                modifier = Modifier.weight(0.3f),
                contentAlignment = Alignment.CenterEnd,
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = stringResource(androidx.compose.ui.R.string.selected),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        // Cover
        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 2.dp)
                .background(
                    color = DividerDefaults.color,
                    shape = MaterialTheme.shapes.small,
                )
                .fillMaxWidth(0.5f)
                .aspectRatio(2f / 3f),
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .size(width = 24.dp, height = 16.dp)
                    .clip(RoundedCornerShape(5.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .background(MaterialTheme.colorScheme.tertiary),
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                )
            }
        }

        // Bottom bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(17.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape,
                            ),
                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .alpha(0.6f)
                            .height(17.dp)
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = MaterialTheme.shapes.small,
                            ),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ThemeCardPreview() {
    AppearanceScreen(
        navController = NavController(LocalContext.current),
    )
}