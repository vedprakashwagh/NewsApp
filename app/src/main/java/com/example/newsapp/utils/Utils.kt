package com.example.newsapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

object Utils {

    fun createNotificationChannel(
        context: Context,
        name: String,
        description: String,
        channelId: String
    ) {
        Firebase.messaging.subscribeToTopic("/topics/all")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                channelId, name,
                importance
            )
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviours after this
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

}

val USER_PREFS = "USER_PREFS"
val PERMISSION_RATIONAL = "PERMISSION_RATIONAL"

var Context.didShowNotificationRationale: Boolean
    get() = getSharedPreferences(
        USER_PREFS,
        Context.MODE_PRIVATE
    ).getBoolean(PERMISSION_RATIONAL, true)
    set(value) {
        getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit()
            .putBoolean(PERMISSION_RATIONAL, value).apply()
    }