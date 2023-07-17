package com.example.newsapp.di.module

import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.repository_impl.NewsRepositoryImpl
import com.example.newsapp.domain.model.repository.NewsRepository
import com.example.newsapp.domain.use_case.get_news.GetNewsDataUseCase
import com.example.newsapp.ui.home.HomeViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val platformDependency = module {
    single {
        localHttpClient
    }
}


val apiDependency = module {
    single {
        NewsApi(get())
    }
}

val repoDependency = module {
    single<NewsRepository> {
        NewsRepositoryImpl(get())
    }
}

val useCaseDependency = module {
    single {
        GetNewsDataUseCase(get())
    }
}

val viewmodelDependency = module {
    viewModel {
        HomeViewmodel(get())
    }
}
