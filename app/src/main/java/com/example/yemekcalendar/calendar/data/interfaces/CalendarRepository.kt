package com.example.yemekcalendar.calendar.data.interfaces

import com.example.yemekcalendar.core.data.models.CalendarDay
import com.example.yemekcalendar.core.data.models.FoodItem
import com.example.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.example.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.example.yemekcalendar.core.other.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    //   Institution
    suspend fun getInstitutionList(): Flow<Resource<List<String>>>
    suspend fun insertInstitution(institution: String)

    suspend fun deleteInstitution(institution: String)

    //    Calendar
    suspend fun getAllCalendarDays(): Flow<Resource<List<CalendarDay>>>
    suspend fun getAllCalendarDaysByInstitution(institution: String): Flow<Resource<List<CalendarDay>>>

    suspend fun insertCalendarDay(day: CalendarDay, institution: String)
    suspend fun insertCalendarDay(day: CalendarDayEntity)

    suspend fun deleteCalendarDay(day: CalendarDay, institution: String)

    suspend fun deleteAllCalendarDaysByInstitution(institution: String)

    //  Food
    suspend fun getAllFoodItems(): Flow<Resource<List<FoodItem>>>
    suspend fun getFoodItemByName(name: String): Flow<Resource<FoodItem?>>
    suspend fun getFoodItemById(id: Int): Flow<Resource<FoodItem?>>

    suspend fun insertFoodItem(foodItem: FoodItem)
    suspend fun insertFoodItem(foodItem: FoodItemEntity)

    suspend fun deleteFoodItem(foodItem: FoodItem)

    suspend fun deleteAllFoodItems()
}