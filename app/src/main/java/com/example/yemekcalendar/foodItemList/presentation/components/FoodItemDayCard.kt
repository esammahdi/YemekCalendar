package com.example.yemekcalendar.foodItemList.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.yemekcalendar.calendar.presentation.components.CalendarDayCard
import com.example.yemekcalendar.core.data.models.CalendarDay
import com.example.yemekcalendar.core.other.utils.getDayOfMonth

@Composable
fun FoodItemDayCard(calendarDay: CalendarDay) {
    var showPopup by remember { mutableStateOf(false) }
    val dayOfMonth = getDayOfMonth(calendarDay.date)

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(48.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
            .clickable {
                showPopup = !showPopup
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayOfMonth,
            color = MaterialTheme.colorScheme.primary,
        )
        DropdownMenu(
            expanded = showPopup,
            onDismissRequest = { showPopup = false },
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.surface)
                .zIndex(-1f),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .size(225.dp)
                    .wrapContentSize()
            ) {
                CalendarDayCard(
                    modifier = Modifier.fillMaxSize(),
                    day = calendarDay,
                    onAddToCalendarClicked = {}
                )
            }
        }
    }
}
