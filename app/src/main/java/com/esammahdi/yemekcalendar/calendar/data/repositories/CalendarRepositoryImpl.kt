package com.esammahdi.yemekcalendar.calendar.data.repositories

import android.content.Context
import com.esammahdi.yemekcalendar.calendar.data.interfaces.CalendarRepository
import com.esammahdi.yemekcalendar.core.data.models.CalendarDay
import com.esammahdi.yemekcalendar.core.data.models.FoodItem
import com.esammahdi.yemekcalendar.core.data.room.TypeConverter
import com.esammahdi.yemekcalendar.core.data.room.daos.CalendarDao
import com.esammahdi.yemekcalendar.core.data.room.daos.FoodItemDao
import com.esammahdi.yemekcalendar.core.data.room.daos.InstitutionDao
import com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.esammahdi.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.esammahdi.yemekcalendar.core.other.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val institutionDao: InstitutionDao,
    private val calendarDao: CalendarDao,
    private val foodItemDao: FoodItemDao
) : CalendarRepository {
    // Instiution
    override suspend fun getInstitutionList(): Flow<Resource<List<String>>> {

        return institutionDao.getAllItems().transform { list ->
            emit(Resource.Loading())
            val institutions = list.map { institutionEntity ->
                TypeConverter().institutionEntityToInstitution(institutionEntity)
            }
            emit(Resource.Success(institutions))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }

    }


    override suspend fun insertInstitution(institution: String) {
        val institutionEntity = TypeConverter().institutionToInstitutionEntity(institution)
        institutionDao.insertItem(institutionEntity)
    }

    override suspend fun deleteInstitution(institution: String) {
        val institutionEntity = TypeConverter().institutionToInstitutionEntity(institution)
        institutionDao.deleteItem(institutionEntity)
    }

    /////////////////////////////////////////////////////////////////////////////
    // Calendar
    override suspend fun getAllCalendarDays(): Flow<Resource<List<CalendarDay>>> {
        val sourceFlow = calendarDao.getAllCalendarDays()
        return sourceFlow.transform {
            emit(Resource.Loading())
            val list = it.map { calendarDayEntity ->
                TypeConverter().calendarDayEntityToCalendarDay(calendarDayEntity)
            }
            emit(Resource.Success(list))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }


    override suspend fun getAllCalendarDaysByInstitution(institution: String): Flow<Resource<List<CalendarDay>>> {
        val sourceFlow = calendarDao.getAllItemsByInstitution(institution)
        return sourceFlow.transform {
            emit(Resource.Loading())
            val calendarDays = it.map { calendarDayEntity ->
                TypeConverter().calendarDayEntityToCalendarDay(calendarDayEntity)
            }
            emit(Resource.Success(calendarDays))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun insertCalendarDay(day: CalendarDay, institution: String) {
        val calendarDayEntity = TypeConverter().calendarDayToCalendarDayEntity(day, institution)
        calendarDao.insertItem(calendarDayEntity)
    }

    override suspend fun insertCalendarDay(day: CalendarDayEntity) {
        calendarDao.insertItem(day)
    }

    override suspend fun deleteCalendarDay(day: CalendarDay, institution: String) {
        val calendarDayEntity = TypeConverter().calendarDayToCalendarDayEntity(day, institution)
        calendarDao.deleteItem(calendarDayEntity)
    }

    override suspend fun deleteAllCalendarDaysByInstitution(institution: String) {
        calendarDao.deleteAllItemsByInstitution(institution)
    }

    /////////////////////////////////////////////////////////////////////////////////
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

    override suspend fun getFoodItemByName(name: String): Flow<Resource<FoodItem?>> = flow {
        emit(Resource.Loading())
        val foodItem =
            foodItemDao.getItemByName(name)?.let { TypeConverter().foodItemEntityToFoodItem(it) }
        emit(Resource.Success(foodItem))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun getFoodItemById(id: Int): Flow<Resource<FoodItem?>> = flow {
        emit(Resource.Loading())
        val foodItem =
            foodItemDao.getItemById(id)?.let { TypeConverter().foodItemEntityToFoodItem(it) }
        emit(Resource.Success(foodItem))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun insertFoodItem(foodItem: FoodItem) {
        val foodItemEntity = TypeConverter().foodItemToFoodItemEntity(foodItem)
        foodItemDao.insertItem(foodItemEntity)
    }

    override suspend fun insertFoodItem(foodItem: FoodItemEntity) {
        foodItemDao.insertItem(foodItem)
    }

    override suspend fun deleteFoodItem(foodItem: FoodItem) {
        val foodItemEntity = TypeConverter().foodItemToFoodItemEntity(foodItem)
        foodItemDao.deleteItem(foodItemEntity)
    }

    override suspend fun deleteAllFoodItems() {
        foodItemDao.deleteAllItems()
    }

}







