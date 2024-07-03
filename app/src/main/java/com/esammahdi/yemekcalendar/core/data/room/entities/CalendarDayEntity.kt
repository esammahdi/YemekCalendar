package com.esammahdi.yemekcalendar.core.data.room.entities

import androidx.room.Entity

@Entity(tableName = "calendar_days", primaryKeys = ["date", "institution"])
data class CalendarDayEntity(
    val institution: String,
    val date: Long,
    val menu: List<FoodItemEntity>,
    val type: String
)

val DefaultCalendarDay = CalendarDayEntity(
    institution = "Error",
    date = 123456789L,
    menu = emptyList(),
    type = "Canceled"
)


data class FirebaseCalendarDayEntity(
    val institution: String = "Default",
    val date: Long = 1678945678L,
    val menu: List<Int> = listOf(1, 2, 3, 4),
    val type: String = "MainCourse"
)