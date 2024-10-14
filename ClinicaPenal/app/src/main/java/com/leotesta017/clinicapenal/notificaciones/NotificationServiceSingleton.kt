package com.leotesta017.clinicapenal.notificaciones

import android.annotation.SuppressLint
import android.content.Context

object NotificationServiceSingleton {
    @SuppressLint("StaticFieldLeak")
    private var notificationService: NotificationService? = null

    fun getInstance(context: Context): NotificationService {
        // Usamos applicationContext para evitar fugas de memoria
        val appContext = context.applicationContext
        return notificationService ?: synchronized(this) {
            notificationService ?: NotificationService(appContext).also { notificationService = it }
        }
    }
}
