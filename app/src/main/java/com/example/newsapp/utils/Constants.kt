package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class Constants {

    object Endpoints {
        const val ENDPOINT = "https://candidate-test-data-moengage.s3.amazonaws.com"
    }

    enum class Sort {
        NONE,
        ASCENDING,
        DESCENDING
    }

    class Notifications {
        companion object {
            const val KEY_NOTIFICATION_TITLE = "NOTIFICATION_TITLE"
            const val KEY_NOTIFICATION_DESCRIPTION = "NOTIFICATION_DESCRIPTION"
            const val KEY_NOTIFICATION_URL = "NOTIFICATION_URL"
        }
    }

}