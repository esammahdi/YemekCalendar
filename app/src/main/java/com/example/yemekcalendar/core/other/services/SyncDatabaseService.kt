package com.example.yemekcalendar.core.other.services

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.example.yemekcalendar.core.data.interfaces.OnlineDatabaseRepository
import com.example.yemekcalendar.core.other.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SyncDatabaseService : Service() {
    private var foodItemsJob: Job? = null
    private var institutionsJob: Job? = null
    private var calendarDaysJob: Job? = null
    private var isAppForeground = false
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var onlineDBRepository: OnlineDatabaseRepository

    private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {
            isAppForeground = true
        }

        override fun onActivityResumed(activity: Activity) {
            isAppForeground = true
        }

        override fun onActivityPaused(activity: Activity) {
            isAppForeground = false
        }

        override fun onActivityStopped(activity: Activity) {
            isAppForeground = false
        }

        override fun onActivityDestroyed(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    }

    override fun onCreate() {
        super.onCreate()
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startBackgroundTasks()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startBackgroundTasks() {
        institutionsJob = serviceScope.launch {
            onlineDBRepository.getInstitutions().collectLatest { result ->
                if (isAppForeground) {
                    showToast(result)
                } else {
                    showNotification(result)
                }

            }
        }

        foodItemsJob = serviceScope.launch {
            onlineDBRepository.getFoodItems().collectLatest { result ->
                if (isAppForeground) {
                    showToast(result)
                } else {
                    showNotification(result)
                }

            }
        }

        calendarDaysJob = serviceScope.launch {
            onlineDBRepository.getCalendarDays().collectLatest { result ->
                if (isAppForeground) {
                    showToast(result)
                } else {
                    showNotification(result)
                }

            }
        }
    }

    private fun <T> showToast(result: Resource<T>) {

    }


    private fun <T> showNotification(result: Resource<T>) {

    }

    override fun onDestroy() {
        foodItemsJob?.cancel()
        institutionsJob?.cancel()
        calendarDaysJob?.cancel()
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        super.onDestroy()
    }

}