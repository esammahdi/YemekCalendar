package com.example.yemekcalendar.core.other.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.yemekcalendar.core.data.interfaces.OnlineDatabaseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit


@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val onlineDatabaseRepository: OnlineDatabaseRepository
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        try {
            onlineDatabaseRepository.refreshInstitutions()
                .collect { /* Collect and handle flow emission */ }
            onlineDatabaseRepository.refreshFoodItems()
                .collect { /* Collect and handle flow emission */ }
            onlineDatabaseRepository.refreshCalendarDays()
                .collect { /* Collect and handle flow emission */ }
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}

// Schedule the periodic work
fun schedulePeriodicRefreshData(context: Context) {
    val periodicWorkRequest =
        PeriodicWorkRequestBuilder<RefreshDataWorker>(2, TimeUnit.HOURS)
            .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "RefreshDataWork",
        ExistingPeriodicWorkPolicy.UPDATE,
        periodicWorkRequest
    )
}
