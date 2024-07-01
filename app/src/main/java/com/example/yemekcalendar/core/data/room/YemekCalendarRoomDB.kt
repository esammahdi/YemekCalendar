package com.example.yemekcalendar.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yemekcalendar.core.data.room.daos.CalendarDao
import com.example.yemekcalendar.core.data.room.daos.FoodItemDao
import com.example.yemekcalendar.core.data.room.daos.InstitutionDao
import com.example.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.example.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.example.yemekcalendar.core.data.room.entities.InstitutionEntity

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