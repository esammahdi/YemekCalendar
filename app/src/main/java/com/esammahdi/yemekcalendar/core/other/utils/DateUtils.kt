package com.esammahdi.yemekcalendar.core.other.utils

import com.esammahdi.yemekcalendar.core.data.models.CalendarDay
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.temporal.ChronoField
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("MMMM d", Locale.getDefault())
    val formattedDate = formatter.format(date)
    return formattedDate
}

fun divideIntoWeeks(calendarDays: List<CalendarDay>): Map<Int, List<CalendarDay>> {
    val map = mutableMapOf<Int, MutableList<CalendarDay>>()

    calendarDays.forEach { calendarDay ->
        // Get the week number for the given date
        val weekOfMonth = calendarDay.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .get(ChronoField.ALIGNED_WEEK_OF_MONTH)
        // Add the day to the corresponding week in the map
        if (map.containsKey(weekOfMonth)) {
            map[weekOfMonth]?.add(calendarDay)
        } else {
            map[weekOfMonth] = mutableListOf(calendarDay)
        }
    }

    return map
}

fun getDayOfMonth(date: Date): String {
    val formatter =
        SimpleDateFormat("d", Locale.getDefault()) // "dd" pattern extracts the day of the month
    return formatter.format(date)
}