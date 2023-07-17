package com.example.newsapp.system.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.newsapp.R
import com.example.newsapp.utils.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class NotificationService : FirebaseMessagingService() {

    companion object {
        val CHANNEL_ID = "NEWS_NOTIFICATIONS"
    }


    /**
     * Since we're not given any particular task to perform after receiving the data payload of
     * notification, we'll just get URL from the Data notification and open it in browser when
     * notification is clicked.
     *
     * We'll also need to perform check of URL that's received from server to not crash the app.
     * Currently if the URL is invalid, the app will crash when notification is clicked.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("NotificationService", "Notification received!")
        if (message.data.isNotEmpty()) {
            val title = message.data.getValue(Constants.Notifications.KEY_NOTIFICATION_TITLE)
            val description =
                message.data.getValue(Constants.Notifications.KEY_NOTIFICATION_DESCRIPTION)
            val url = message.data.getValue(Constants.Notifications.KEY_NOTIFICATION_URL)

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }

            val pendingIntent = PendingIntent.getActivity(
                this,
                0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(
                this, CHANNEL_ID
            ).apply {
                setContentTitle(title)
                setContentText(description)
                priority = NotificationCompat.PRIORITY_HIGH
                setContentIntent(pendingIntent)
                setAutoCancel(true)
                setStyle(NotificationCompat.BigTextStyle().bigText(description))
                setSmallIcon(R.drawable.baseline_newspaper_24)
            }

            val mNotificationManager =
                this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(Random.nextInt(Integer.MAX_VALUE), builder.build())
        }

    }

}