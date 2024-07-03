package com.esammahdi.yemekcalendar.core.other.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.esammahdi.yemekcalendar.authentication.data.interfaces.AuthRepository
import com.esammahdi.yemekcalendar.authentication.data.repositories.FirebaseAuthRepositoryImpl
import com.esammahdi.yemekcalendar.calendar.data.interfaces.CalendarRepository
import com.esammahdi.yemekcalendar.calendar.data.repositories.CalendarRepositoryImpl
import com.esammahdi.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.esammahdi.yemekcalendar.core.data.interfaces.OnlineDatabaseRepository
import com.esammahdi.yemekcalendar.core.data.interfaces.OnlineStorageRepository
import com.esammahdi.yemekcalendar.core.data.repositories.FirebaseOnlineDBRepositoryImpl
import com.esammahdi.yemekcalendar.core.data.repositories.FirebaseOnlineStorageRepositoryImpl
import com.esammahdi.yemekcalendar.core.data.repositories.LocalUserRepositoryImpl
import com.esammahdi.yemekcalendar.core.data.room.YemekCalendarRoomDB
import com.esammahdi.yemekcalendar.core.data.room.daos.CalendarDao
import com.esammahdi.yemekcalendar.core.data.room.daos.FoodItemDao
import com.esammahdi.yemekcalendar.core.data.room.daos.InstitutionDao
import com.esammahdi.yemekcalendar.foodItemList.data.interfaces.FoodItemsRepository
import com.esammahdi.yemekcalendar.foodItemList.data.repositories.FoodItemsRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Firebase
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseRealtimeDatabase() = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return FirebaseAuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesOnlineDatabaseRepository(
        firebaseDatabase: FirebaseDatabase,
        foodItemDao: FoodItemDao,
        calendarDao: CalendarDao,
        institutionDao: InstitutionDao
    ): OnlineDatabaseRepository {
        return FirebaseOnlineDBRepositoryImpl(
            firebaseDatabase = firebaseDatabase,
            foodItemDao = foodItemDao,
            calendarDao = calendarDao,
            institutionDao = institutionDao
        )
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DataStore

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Repositories

    @Provides
    @Singleton
    fun providesOnlineStorageRepository(
        firebaseAuth: FirebaseAuth,
        firebaseStorage: FirebaseStorage,
    ): OnlineStorageRepository {

        return FirebaseOnlineStorageRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseStorage = firebaseStorage,
        )
    }

    @Provides
    @Singleton
    fun providesLocalUserRepository(
        @ApplicationContext appContext: Context,
        dataStore: DataStore<Preferences>,
    ): LocalUserRepository {
        return LocalUserRepositoryImpl(
            appContext = appContext,
            dataStore = dataStore
        )
    }

    @Provides
    @Singleton
    fun providesCalendarRepository(
        @ApplicationContext appContext: Context,
        foodItemDao: FoodItemDao,
        calendarDao: CalendarDao,
        institutionDao: InstitutionDao
    ): CalendarRepository {
        return CalendarRepositoryImpl(
            appContext = appContext,
            institutionDao = institutionDao,
            calendarDao = calendarDao,
            foodItemDao = foodItemDao
        )
    }

    @Provides
    @Singleton
    fun providesFoodItemsRepository(
        foodItemDao: FoodItemDao,
    ): FoodItemsRepository {
        return FoodItemsRepositoryImpl(
            foodItemDao = foodItemDao,
        )
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Room

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): YemekCalendarRoomDB {
        return Room.databaseBuilder(
            appContext,
            YemekCalendarRoomDB::class.java,
            "yemekcalendar_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodItemDao(appDatabase: YemekCalendarRoomDB): FoodItemDao {
        return appDatabase.foodItemDao()
    }

    @Provides
    @Singleton
    fun provideCalendarDao(appDatabase: YemekCalendarRoomDB): CalendarDao {
        return appDatabase.calendarDayDao()
    }

    @Provides
    @Singleton
    fun provideInstitutionDao(appDatabase: YemekCalendarRoomDB): InstitutionDao {
        return appDatabase.institutionDao()
    }

}