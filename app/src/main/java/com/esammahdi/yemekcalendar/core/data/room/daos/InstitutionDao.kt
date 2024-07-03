package com.esammahdi.yemekcalendar.core.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esammahdi.yemekcalendar.core.data.room.entities.InstitutionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InstitutionDao {
    @Query("SELECT * FROM institution")
    fun getAllItems(): Flow<List<InstitutionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(institution: InstitutionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<InstitutionEntity>)

    @Delete
    suspend fun deleteItem(institution: InstitutionEntity)

    @Query("DELETE FROM institution")
    suspend fun deleteAllItems()
}