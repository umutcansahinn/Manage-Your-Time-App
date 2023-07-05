package com.umutcansahin.manageyourtime

import android.app.Application
import com.umutcansahin.manageyourtime.di.coroutineDispatcherModule
import com.umutcansahin.manageyourtime.di.localModule
import com.umutcansahin.manageyourtime.di.repositoryModule
import com.umutcansahin.manageyourtime.di.useCaseModule
import com.umutcansahin.manageyourtime.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                localModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                coroutineDispatcherModule
            )
        }
    }
}