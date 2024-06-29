package com.example.yemekcalendar.core.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yemekcalendar.core.data.room.entities.FoodItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {
    @Query("SELECT * FROM food_items")
    fun getAllItems(): Flow<List<FoodItemEntity>>

    @Query("SELECT * FROM food_items WHERE name = :name")
    suspend fun getItemByName(name: String): FoodItemEntity?

    @Query("SELECT * FROM food_items WHERE id = :id")
    suspend fun getItemById(id: Int): FoodItemEntity?

    @Query("SELECT * FROM food_items WHERE id IN (:list)")
    fun getItemsByListOfIds(list: List<Int>): Flow<List<FoodItemEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FoodItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<FoodItemEntity>)

    @Delete
    suspend fun deleteItem(item: FoodItemEntity)

    @Update
    suspend fun updateItem(item: FoodItemEntity)

    @Query("DELETE FROM food_items")
    suspend fun deleteAllItems()

}