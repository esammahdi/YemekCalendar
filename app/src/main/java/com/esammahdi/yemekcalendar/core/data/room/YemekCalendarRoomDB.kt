package com.esammahdi.yemekcalendar.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esammahdi.yemekcalendar.core.data.room.daos.CalendarDao
import com.esammahdi.yemekcalendar.core.data.room.daos.FoodItemDao
import com.esammahdi.yemekcalendar.core.data.room.daos.InstitutionDao
import com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.esammahdi.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.esammahdi.yemekcalendar.core.data.room.entities.InstitutionEntity

@Database(
    entities = [FoodItemEntity::class, CalendarDayEntity::class, InstitutionEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(FoodItemEntityTypeConverter::class)
abstract class YemekCalendarRoomDB : RoomDatabase() {
    abstract fun foodItemDao(): FoodItemDao
    abstract fun calendarDayDao(): CalendarDao
    abstract fun institutionDao(): InstitutionDao
}