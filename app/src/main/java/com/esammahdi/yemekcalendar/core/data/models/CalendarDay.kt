package com.esammahdi.yemekcalendar.core.data.models

import java.util.Date

data class CalendarDay(
    val date: Date,
    val menu: List<FoodItem>,
    val type: DayType,
)

enum class DayType {
    Normal,
    Weekend,
    SpecialHoliday,
    Updated,
    Canceled
}
