package com.example.newsapp.domain.use_case.get_news

import android.util.Log
import com.example.newsapp.domain.model.repository.NewsRepository
import com.example.newsapp.domain.model.responses.ModelNews
import com.example.newsapp.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetNewsDataUseCase(private val repository: NewsRepository) {

    operator fun invoke(): Flow<ResourceState<ModelNews, Exception?>> = flow {
        try {
            emit(ResourceState.Loading())
            val newsData = repository.getNewsData()
            if (newsData != null) {
                emit(ResourceState.Success(newsData))
            } else {
                emit(ResourceState.Failed(Exception("Received empty response")))
            }

        } catch (e: IOException) {
            Log.e("GetNewsDataUseCase", e.stackTraceToString())
            emit(ResourceState.Failed(Exception("Couldn't reach server. Is your internet on?")))
        } catch (e: Exception) {
            Log.e("GetNewsDataUseCase", e.stackTraceToString())
            emit(ResourceState.Failed(e))
        }
    }

}