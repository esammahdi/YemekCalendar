package com.esammahdi.yemekcalendar.calendar.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.core.data.models.CalendarDay
import com.esammahdi.yemekcalendar.core.data.models.DayType
import com.esammahdi.yemekcalendar.core.other.utils.formatDate

@Composable
fun CalendarDayCard(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    onAddToCalendarClicked: () -> Unit
) {
    Card(
        modifier = modifier.let {
            if (day.type == DayType.Updated) {
                it.border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
            } else {
                it
            }
        },
        elevation = if (day.type == DayType.Updated) CardDefaults.cardElevation(defaultElevation = 10.dp) else CardDefaults.cardElevation(),
    ) {
        when (day.type) {
            DayType.Normal -> NormalDayCard(
                day,
                onAddToCalendarClicked = {
                    onAddToCalendarClicked()
                }
            )

            DayType.Updated -> UpdatedDayCard(
                day,
                onAddToCalendarClicked = {
                    onAddToCalendarClicked()
                }
            )

            DayType.Weekend -> WeekendDayCard(day)
            DayType.SpecialHoliday -> SpecialHolidayDayCard(day)
            DayType.Canceled -> CanceledDayCard(day)
        }
    }
}

@Composable
fun NormalDayCard(
    day: CalendarDay,
    onAddToCalendarClicked: () -> Unit
) {
    val totalCalories = remember(day) { day.menu.sumOf { it.calories } }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
//                .height(48.dp)
            ,
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(day.date),
                    modifier = Modifier
                        .padding(10.dp),
                    style = MaterialTheme.typography.titleSmall,
                )

                IconButton(
                    onClick = {
                        onAddToCalendarClicked()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_calendar_add_on_24),
                        contentDescription = "Add to Calendar Icon Button",
                    )
                }
            }
        }

        for (foodItem in day.menu) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = foodItem.name,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "${foodItem.calories}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }

        //Make a Text for total calories
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.total_calories),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "$totalCalories Kcal",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun UpdatedDayCard(
    day: CalendarDay,
    onAddToCalendarClicked: () -> Unit
) {
    val totalCalories = remember(day) { day.menu.sumOf { it.calories } }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
//                .height(48.dp)
            ,
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(day.date),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(start = 10.dp),
                    style = MaterialTheme.typography.titleSmall,
                )

                Spacer(modifier = Modifier.width(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .widthIn(50.dp),
                            text = stringResource(R.string.updated),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.labelSmall,
                            fontStyle = FontStyle.Italic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(
                        onClick = {
                            onAddToCalendarClicked()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_calendar_add_on_24),
                            contentDescription = "Add to Calendar Icon Button",
                        )
                    }
                }
            }
        }

        for (foodItem in day.menu) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = foodItem.name,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "${foodItem.calories}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }

        //Make a Text for total calories
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.total_calories),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "$totalCalories Kcal",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun SpecialHolidayDayCard(day: CalendarDay) {
    BaseNotServedDayCard(
        day = day,
        imageResId = R.drawable.holiday,
        message = stringResource(R.string.special_holiday_no_service_message)
    )
}

@Composable
fun WeekendDayCard(day: CalendarDay) {
    BaseNotServedDayCard(
        day = day,
        imageResId = R.drawable.weekend,
        message = stringResource(R.string.weekend_no_service_message)
    )
}

@Composable
fun CanceledDayCard(day: CalendarDay) {
    BaseNotServedDayCard(
        day = day,
        imageResId = R.drawable.canceled,
        message = stringResource(R.string.canceled_service_message),
        additionalMessage = stringResource(R.string.contact_adminstrator)
    )
}

@Composable
fun BaseNotServedDayCard(
    day: CalendarDay,
    imageResId: Int,
    message: String,
    additionalMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(day.date),
                    modifier = Modifier
                        .padding(10.dp),
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(imageResId),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            additionalMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

