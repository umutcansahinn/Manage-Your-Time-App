package com.umutcansahin.manageyourtime

import android.app.Application
import com.umutcansahin.manageyourtime.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                localModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
            )
        }
    }
}