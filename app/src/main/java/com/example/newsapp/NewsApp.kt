package com.example.newsapp

import android.app.Application
import android.content.Context
import com.example.newsapp.di.module.apiDependency
import com.example.newsapp.di.module.platformDependency
import com.example.newsapp.di.module.repoDependency
import com.example.newsapp.di.module.useCaseDependency
import com.example.newsapp.di.module.viewmodelDependency
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }

    private fun initKoin(context: Context) = startKoin {
        androidLogger()
        androidContext(context)
        modules(
            platformDependency,
            apiDependency,
            repoDependency,
            useCaseDependency,
            viewmodelDependency
        )
    }
}