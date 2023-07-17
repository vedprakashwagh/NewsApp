package com.example.newsapp.data.remote

import com.example.newsapp.di.module.LocalHttpClient
import com.example.newsapp.domain.model.responses.ModelNews

class NewsApi(private val httpClient: LocalHttpClient) {

    suspend fun getNewsData(): ModelNews? {
        val builder = LocalHttpClient.RequestBuilder()
            .apply {
                url("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
                requestMethod(LocalHttpClient.RequestMethods.GET)
            }
        return httpClient.request(builder = builder, clazz = ModelNews::class.java)
    }

}