package com.example.newsapp.domain.model.repository

import com.example.newsapp.domain.model.responses.ModelNews

interface NewsRepository {
    suspend fun getNewsData(): ModelNews?
}