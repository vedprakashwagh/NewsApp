package com.example.newsapp.domain.model.responses

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Missing serialization. In release add Serialization to this class.
 */

data class ModelNews(
    val status: String,
    var articles: List<Article>?
)

data class Article(
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
) {
    fun getFormattedDate(): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(publishedAt)
            ?: Date()
    }

    fun getDisplayDate(): String {
        return SimpleDateFormat("MMM dd, yyyy HH:mma", Locale.getDefault()).format(getFormattedDate())
    }

}

data class Source(
    val id: String,
    val name: String
)