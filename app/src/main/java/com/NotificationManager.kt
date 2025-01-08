package com

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequestBuilder

fun scheduleNotification(context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
    WorkManager.getInstance(context).enqueue(workRequest)
}

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        // Create and send notification here
        return Result.success()
    }
}
