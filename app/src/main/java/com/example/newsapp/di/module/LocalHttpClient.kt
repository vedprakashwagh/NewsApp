package com.example.newsapp.di.module

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.reflect.KClass

val localHttpClient = LocalHttpClient()

class LocalHttpClient {

    /**
     * This is an extremely simple implementation and lacks a lot of fine details.
     * In real world this will throw an exception if there's any with proper message and will be non-nullable.
     */
    suspend fun <E> request(builder: RequestBuilder, clazz: Class<E>): E? {
        when (builder.requestMethod) {
            RequestMethods.GET -> {
                return get(builder = builder, clazz = clazz)
            }

            RequestMethods.POST -> {
                return null
            }

            RequestMethods.PUT -> {
                return null
            }

            RequestMethods.DELETE -> {
                return null
            }
        }
    }

    private suspend fun <E> get(builder: RequestBuilder, clazz: Class<E>): E? =
        withContext(Dispatchers.IO) {
            with(URL(builder.url).openConnection() as HttpsURLConnection) {
                requestMethod = "GET"
                val response = StringBuffer()
                BufferedReader(InputStreamReader(inputStream)).use {
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                }
                return@with Gson().fromJson(response.toString(), clazz)
            }
        }

    class RequestBuilder {

        var url: String? = null
            private set
        var requestMethod: RequestMethods = RequestMethods.GET
            private set

        fun url(url: String) = apply {
            this.url = url
        }

        fun requestMethod(method: RequestMethods) = apply {
            this.requestMethod = method
        }

    }

    enum class RequestMethods {
        GET, POST, PUT, DELETE
    }

}