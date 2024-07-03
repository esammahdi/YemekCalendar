package com.esammahdi.yemekcalendar.calendar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.calendar.presentation.components.CalendarShimmerLoadingScreen
import com.esammahdi.yemekcalendar.calendar.presentation.components.DayGrid
import com.esammahdi.yemekcalendar.calendar.presentation.components.MonthNavigationBar
import com.esammahdi.yemekcalendar.core.presentation.components.ErrorScreen
import com.esammahdi.yemekcalendar.core.presentation.components.NoDataScreen
import com.esammahdi.yemekcalendar.core.presentation.components.Title
import com.esammahdi.yemekcalendar.core.presentation.components.YemekCalendarDropdownList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    calendarViewModel.initialize()
    val calendarUiState by calendarViewModel.uiState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState()

    if (calendarUiState.isAddToCalendarSuccess) {
        AlertDialog(
            onDismissRequest = {
                calendarViewModel.onAddToCalendarSuccessDialogDismissed()
            },
            title = { Text(text = stringResource(id = R.string.add_to_calendar_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.add_to_calendar_dialog_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        calendarViewModel.onAddToCalendarSuccessDialogDismissed()
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

    PullToRefreshBox(
        isRefreshing = calendarUiState.isRefreshing,
        onRefresh = { calendarViewModel.onRefresh() },
        state = pullRefreshState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
        ) {

            // Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Title(
                    text = stringResource(R.string.calendar),
                    modifier = Modifier.padding(16.dp)
                )
            }

            when {
                calendarUiState.isLoading -> {
                    CalendarShimmerLoadingScreen()
                }

                calendarUiState.isError -> {
                    ErrorScreen(calendarUiState.errorMessage)
                }

                calendarUiState.institutionList.isEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            NoDataScreen()
                        }
                    }
                }

                else -> {
                    InstitutionDropdown(calendarUiState, calendarViewModel)
                    MonthNavigation(
                        calendarUiState = calendarUiState,
                        calendarViewModel = calendarViewModel
                    )
                    DayGrid(
                        calendarDays = calendarUiState.calendarDays[calendarUiState.selectedMonth],
                        gridState = calendarUiState.gridState,
                    )
                }
            }
        }
    }
}

@Composable
fun InstitutionDropdown(
    calendarUiState: CalendarState,
    calendarViewModel: CalendarViewModel,
) {
    YemekCalendarDropdownList(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        wrapContentWidth = false,
        itemList = calendarUiState.institutionList,
        selectedItemText = calendarUiState.selectedInstitution,
        onSelectedIndexChanged = { calendarViewModel.onInstitutionSelected(it) },
    )
}

@Composable
fun MonthNavigation(
    modifier: Modifier = Modifier,
    calendarUiState: CalendarState,
    calendarViewModel: CalendarViewModel
) {
    MonthNavigationBar(
        modifier = modifier,
        month = calendarUiState.selectedMonth,
        onBackArrowClicked = { calendarViewModel.onBackwardArrowClicked() },
        onForwardArrowClicked = { calendarViewModel.onForwardArrowClicked() }
    )
}


