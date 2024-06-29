package com.example.yemekcalendar.foodItemList.data.interfaces

import com.example.yemekcalendar.core.data.models.FoodItem
import com.example.yemekcalendar.core.other.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FoodItemsRepository {
    suspend fun getAllFoodItems(): Flow<Resource<List<FoodItem>>>
    suspend fun getFoodItemByName(name: String): Flow<Resource<FoodItem>>
    suspend fun getFoodItemById(id: Int): Flow<Resource<FoodItem>>
}