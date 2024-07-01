package com.example.yemekcalendar.calendar.presentation.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.data.models.CalendarDay
import com.example.yemekcalendar.core.other.utils.divideIntoWeeks
import com.example.yemekcalendar.core.other.utils.formatDate
import com.example.yemekcalendar.core.presentation.components.BackToTopButton
import com.example.yemekcalendar.core.presentation.components.Header
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.util.TimeZone
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DayGrid(
    calendarDays: List<CalendarDay>?,
    gridState: LazyGridState = rememberLazyGridState(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val calendarEventInsertionSuccessMessage =
        stringResource(R.string.calendar_event_insertion_success_message)
    val permissionDeniedMessage = stringResource(R.string.calendar_permission_denied_message)
    var shouldShowRationaleDialog by remember { mutableStateOf(false) }

    val calendarPermissionsStateList = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
    )

    var addToCalendarPendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == -1) {
            //TODO: Fix this. No message shows when the user does not give permission.
            Toast.makeText(context, permissionDeniedMessage, Toast.LENGTH_SHORT).show()
        }
    }



    if (shouldShowRationaleDialog) {
        AlertDialog(
            onDismissRequest = { shouldShowRationaleDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.permission_required)
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.calendar_permission_rationale)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            calendarPermissionsStateList.launchMultiplePermissionRequest()
                            shouldShowRationaleDialog = false
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.ok)
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { shouldShowRationaleDialog = false }
                ) {
                    Text(
                        stringResource(R.string.cancel)
                    )
                }
            }
        )
    }

    LaunchedEffect(calendarPermissionsStateList.allPermissionsGranted) {
        if (calendarPermissionsStateList.allPermissionsGranted) {
            addToCalendarPendingAction?.invoke()
            addToCalendarPendingAction = null
        }
    }

    if (calendarDays.isNullOrEmpty()) {
        EmptyCalendarMessage()
    } else {

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val columns = calculateColumns(screenWidth, 150.dp, 250.dp)

        val weeksMap = divideIntoWeeks(calendarDays).toSortedMap()
            .mapValues { (_, days) ->
                days.sortedBy { it.date }
            }

        val showBackToTopButton by remember {
            derivedStateOf {
                gridState.firstVisibleItemIndex > 0
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(columns),
                state = gridState
            ) {
                weeksMap.forEach { (week, days) ->
                    // Week header item
                    item(week, span = { GridItemSpan(maxLineSpan) }) {
                        Header(
                            text = (stringResource(R.string.week) + week),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
                        )
                    }

                    items(days) { calendarDay ->
                        CalendarDayCard(
                            modifier = Modifier
                                .aspectRatio(1f),
                            day = calendarDay,
                            onAddToCalendarClicked = {
                                val intent = createCalendarEventIntent(calendarDay, context)
                                if (calendarPermissionsStateList.allPermissionsGranted) {
                                    launcher.launch(intent)
                                } else {
                                    addToCalendarPendingAction = { launcher.launch(intent) }

                                    if (calendarPermissionsStateList.shouldShowRationale) {
                                        shouldShowRationaleDialog = true
                                    } else {
                                        scope.launch {
                                            calendarPermissionsStateList.launchMultiplePermissionRequest()
                                        }
                                    }
                                }
                            }
                        )
                    }

                }
            }


            AnimatedVisibility(
                visible = showBackToTopButton,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BackToTopButton {
                    scope.launch {
                        gridState.animateScrollToItem(0)
                    }
                }
            }

        }
    }
}

@Composable
fun EmptyCalendarMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.empty_calendar_message),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun createCalendarEventIntent(calendarDay: CalendarDay, context: Context): Intent {
    val startTime = calendarDay.date.toInstant().atZone(ZoneId.systemDefault())
    val endTime = startTime.plusDays(1)

    return Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.toEpochSecond() * 1000)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.toEpochSecond() * 1000)
        putExtra(
            CalendarContract.Events.TITLE,
            "${formatDate(calendarDay.date)} ${context.getString(R.string.menu)}"
        )
        putExtra(
            CalendarContract.Events.DESCRIPTION,
            calendarDay.menu.joinToString(separator = "\n") { "${it.name}: ${it.calories} cal" }
        )
        putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}

@Composable
fun calculateColumns(screenWidth: Dp, minCardSize: Dp, maxCardSize: Dp): Int {
    val minColumns = (screenWidth / minCardSize).toInt()
    val maxColumns = (screenWidth / maxCardSize).toInt()
    return max(2, min(minColumns, maxColumns)) // Ensure at least 2 columns
}