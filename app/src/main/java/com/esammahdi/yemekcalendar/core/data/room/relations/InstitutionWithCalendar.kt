package com.esammahdi.yemekcalendar.core.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.esammahdi.yemekcalendar.core.data.room.entities.InstitutionEntity

data class InstitutionWithCalendar(
    @Embedded val calendar: InstitutionEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "institution"
    )
    val calendarDays: List<CalendarDayEntity>
)