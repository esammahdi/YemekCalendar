package com.example.yemekcalendar.foodItemList.data.repositories

import com.example.yemekcalendar.core.data.models.FoodItem
import com.example.yemekcalendar.core.data.room.TypeConverter
import com.example.yemekcalendar.core.data.room.daos.FoodItemDao
import com.example.yemekcalendar.core.other.utils.Resource
import com.example.yemekcalendar.foodItemList.data.interfaces.FoodItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class FoodItemsRepositoryImpl @Inject constructor(
    private val foodItemDao: FoodItemDao
) : FoodItemsRepository {

    // Food
    override suspend fun getAllFoodItems(): Flow<Resource<List<FoodItem>>> {
        val sourceFlow = foodItemDao.getAllItems()
        return sourceFlow.transform {
            emit(Resource.Loading())
            val list =
                it.map { foodItemEntity -> TypeConverter().foodItemEntityToFoodItem(foodItemEntity) }
            emit(Resource.Success(list))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun getFoodItemByName(name: String): Flow<Resource<FoodItem>> = flow {
        emit(Resource.Loading())
        val foodItem =
            foodItemDao.getItemByName(name)?.let { TypeConverter().foodItemEntityToFoodItem(it) }
        if (foodItem != null) {
            emit(Resource.Success(foodItem))
        } else {
            emit(Resource.Error("Food item not found"))
        }
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun getFoodItemById(id: Int): Flow<Resource<FoodItem>> = flow {
        emit(Resource.Loading())
        val foodItem =
            foodItemDao.getItemById(id)?.let { TypeConverter().foodItemEntityToFoodItem(it) }
        if (foodItem != null) {
            emit(Resource.Success(foodItem))
        } else {
            emit(Resource.Error("Food item not found"))
        }
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }
}