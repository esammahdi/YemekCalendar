package com.esammahdi.yemekcalendar

import android.app.Application
import android.content.Intent
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.esammahdi.yemekcalendar.core.other.services.SyncDatabaseService
import com.esammahdi.yemekcalendar.core.other.utils.ToastHelper
import com.esammahdi.yemekcalendar.core.other.workers.schedulePeriodicRefreshData
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class YemekCalendarApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        ToastHelper.init(this)
        startService(Intent(this, SyncDatabaseService::class.java))
        schedulePeriodicRefreshData(this)
    }
}