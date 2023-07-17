package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.responses.ModelNews
import com.example.newsapp.domain.use_case.get_news.GetNewsDataUseCase
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewmodel(
    private val getNewsDataUseCase: GetNewsDataUseCase
) : ViewModel() {

    val news = MutableStateFlow<ResourceState<ModelNews, Exception?>>(ResourceState.Loading())

    fun onEvent(event: HomeEvents) {

        when (event) {
            HomeEvents.FetchNews -> {
                if (!news.value.isSuccess()) {
                    getNewsDataUseCase().onEach {
                        news.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

}