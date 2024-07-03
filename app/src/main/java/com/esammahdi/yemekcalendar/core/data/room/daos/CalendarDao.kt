package com.esammahdi.yemekcalendar.core.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar_days")
    fun getAllCalendarDays(): Flow<List<CalendarDayEntity>>

    @Query("SELECT * FROM calendar_days WHERE institution = :institution")
    fun getAllItemsByInstitution(institution: String): Flow<List<CalendarDayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CalendarDayEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<CalendarDayEntity>)

    @Delete
    suspend fun deleteItem(item: CalendarDayEntity)

    @Update
    suspend fun updateItem(item: CalendarDayEntity)

    @Query("DELETE FROM calendar_days WHERE institution = :institution")
    suspend fun deleteAllItemsByInstitution(institution: String)

    @Query("DELETE FROM calendar_days")
    suspend fun deleteAllItems()
}