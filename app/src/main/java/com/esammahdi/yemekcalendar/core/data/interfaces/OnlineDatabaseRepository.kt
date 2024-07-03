package com.esammahdi.yemekcalendar.core.data.interfaces

import com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.esammahdi.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.esammahdi.yemekcalendar.core.other.utils.Resource
import kotlinx.coroutines.flow.Flow

interface OnlineDatabaseRepository {
    // Pull all data from the online database and insert it into the local database.
    suspend fun getInstitutions(): Flow<Resource<List<String>?>>

    suspend fun getFoodItems(): Flow<Resource<List<FoodItemEntity>?>>

    suspend fun getFoodItemById(id: Int): Flow<Resource<FoodItemEntity?>>

    suspend fun getCalendarDays(): Flow<Resource<List<CalendarDayEntity>?>>

    suspend fun refreshInstitutions(): Flow<Resource<List<String>?>>

    suspend fun refreshFoodItems(): Flow<Resource<List<FoodItemEntity>?>>

    suspend fun refreshCalendarDays(): Flow<Resource<List<CalendarDayEntity>?>>

}