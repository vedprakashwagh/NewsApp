package com.example.newsapp.data.repository_impl

import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.domain.model.repository.NewsRepository
import com.example.newsapp.domain.model.responses.ModelNews

class NewsRepositoryImpl(val api: NewsApi) : NewsRepository {
    override suspend fun getNewsData(): ModelNews? {
        return api.getNewsData()
    }
}