package com.github.hanalee.exchangerateproject


import android.app.Application
import com.github.hanalee.exchangerateproject.di.module.repositoryModule
import com.github.hanalee.exchangerateproject.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }

    }
}