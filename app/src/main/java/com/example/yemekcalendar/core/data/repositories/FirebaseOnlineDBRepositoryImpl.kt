package com.example.yemekcalendar.core.data.repositories


import com.example.yemekcalendar.core.data.interfaces.OnlineDatabaseRepository
import com.example.yemekcalendar.core.data.room.daos.CalendarDao
import com.example.yemekcalendar.core.data.room.daos.FoodItemDao
import com.example.yemekcalendar.core.data.room.daos.InstitutionDao
import com.example.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.example.yemekcalendar.core.data.room.entities.DefaultFoodItemEntity
import com.example.yemekcalendar.core.data.room.entities.FirebaseCalendarDayEntity
import com.example.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.example.yemekcalendar.core.data.room.entities.InstitutionEntity
import com.example.yemekcalendar.core.other.utils.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseOnlineDBRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
    private val foodItemDao: FoodItemDao,
    private val calendarDao: CalendarDao,
    private val institutionDao: InstitutionDao
) : OnlineDatabaseRepository {
    override suspend fun getInstitutions(): Flow<Resource<List<String>?>> = flow {
        emit(Resource.Loading())
        try {
            val institutions = getValueFromFirebase<List<String>>("institutions") { snapshot ->
                snapshot.children.mapNotNull { it.getValue(String::class.java) }
            }
            institutionDao.deleteAllItems()
            institutionDao.insertItems(institutions.map { InstitutionEntity(it) })
            emit(Resource.Success(institutions))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun getFoodItems(): Flow<Resource<List<FoodItemEntity>?>> = flow {
        emit(Resource.Loading())
        try {
            val foodItems = getValueFromFirebase<List<FoodItemEntity>>("food_items") { snapshot ->
                snapshot.children.mapNotNull { it.getValue(FoodItemEntity::class.java) }
            }
            foodItemDao.deleteAllItems()
            foodItemDao.insertItems(foodItems)
            emit(Resource.Success(foodItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun getFoodItemById(id: Int): Flow<Resource<FoodItemEntity?>> = flow {
        emit(Resource.Loading())
        try {
            val foodItem = getValueFromFirebase<FoodItemEntity?>("food_items/$id") { snapshot ->
                snapshot.getValue(FoodItemEntity::class.java)
            }
            foodItem?.let { foodItemDao.insertItem(it) }
            emit(Resource.Success(foodItem))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun getCalendarDays(): Flow<Resource<List<CalendarDayEntity>?>> = flow {
        emit(Resource.Loading())
        try {
            val calendarDays =
                getValueFromFirebase<List<FirebaseCalendarDayEntity>>("calendarDays") { snapshot ->
                    snapshot.children.mapNotNull { it.getValue(FirebaseCalendarDayEntity::class.java) }
                }.map {
                    val menu = it.menu.map { foodItemId ->
                        foodItemDao.getItemById(foodItemId) ?: DefaultFoodItemEntity
                    }
                    CalendarDayEntity(
                        it.institution,
                        it.date,
                        menu,
                        it.type,
                    )
                }

            calendarDao.deleteAllItems()
            calendarDao.insertItems(calendarDays)
            emit(Resource.Success(calendarDays))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    private suspend fun <T> getValueFromFirebase(
        path: String,
        parseSnapshot: (DataSnapshot) -> T
    ): T = suspendCancellableCoroutine { continuation ->
        val databaseReference = firebaseDatabase.getReference("YemekCalendar")
        val ref = databaseReference.child(path)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val data = parseSnapshot(snapshot)
                    continuation.resume(data)
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(error.toException())
            }
        }
        ref.addValueEventListener(listener)
        continuation.invokeOnCancellation { ref.removeEventListener(listener) }
    }

    override suspend fun refreshInstitutions(): Flow<Resource<List<String>?>> = flow {
        emit(Resource.Loading())
        try {
            val institutions = refreshValueFromFirebase<List<String>>("institutions") { snapshot ->
                snapshot.children.mapNotNull { it.getValue(String::class.java) }
            }
            institutionDao.deleteAllItems()
            institutionDao.insertItems(institutions.map { InstitutionEntity(it) })
            emit(Resource.Success(institutions))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun refreshFoodItems(): Flow<Resource<List<FoodItemEntity>?>> = flow {
        emit(Resource.Loading())
        try {
            val foodItems =
                refreshValueFromFirebase<List<FoodItemEntity>>("food_items") { snapshot ->
                    snapshot.children.mapNotNull { it.getValue(FoodItemEntity::class.java) }
                }
            foodItemDao.deleteAllItems()
            foodItemDao.insertItems(foodItems)
            emit(Resource.Success(foodItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun refreshCalendarDays(): Flow<Resource<List<CalendarDayEntity>?>> = flow {
        emit(Resource.Loading())
        try {
            val calendarDays =
                refreshValueFromFirebase<List<FirebaseCalendarDayEntity>>("calendarDays") { snapshot ->
                    snapshot.children.mapNotNull { it.getValue(FirebaseCalendarDayEntity::class.java) }
                }.map {
                    val menu = it.menu.map { foodItemId ->
                        foodItemDao.getItemById(foodItemId) ?: DefaultFoodItemEntity
                    }
                    CalendarDayEntity(
                        it.institution,
                        it.date,
                        menu,
                        it.type,
                    )
                }

            calendarDao.deleteAllItems()
            calendarDao.insertItems(calendarDays)
            emit(Resource.Success(calendarDays))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    private suspend fun <T> refreshValueFromFirebase(
        path: String,
        parseSnapshot: (DataSnapshot) -> T
    ): T = suspendCancellableCoroutine { continuation ->
        val databaseReference = firebaseDatabase.getReference("YemekCalendar")
        val ref = databaseReference.child(path)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val data = parseSnapshot(snapshot)
                    continuation.resume(data)
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(error.toException())
            }
        }
        ref.addListenerForSingleValueEvent(listener)
        continuation.invokeOnCancellation { ref.removeEventListener(listener) }
    }

}




