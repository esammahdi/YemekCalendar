package com.esammahdi.yemekcalendar.foodItemList.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.calendar.presentation.Months
import com.esammahdi.yemekcalendar.core.data.models.CalendarDay
import com.esammahdi.yemekcalendar.core.presentation.components.Header

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodItemCalendar(daysServed: Map<Months, List<CalendarDay>>) {
    if (daysServed.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_food_served_message),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {

        val sortedDays = daysServed.mapValues { (_, days) ->
            days.sortedBy { it.date }
        }.toSortedMap()

        sortedDays.forEach { (month, days) ->
            // Month header item
            Header(
                text = month.name,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
            )

            // Day items in a FlowRow
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (days.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.no_food_served_this_month_message),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    days.forEach { day ->
                        FoodItemDayCard(
                            day
                        )
                    }
                }
            }
        }

    }
}

