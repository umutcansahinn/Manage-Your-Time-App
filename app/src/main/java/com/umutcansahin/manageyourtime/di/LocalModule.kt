package com.umutcansahin.manageyourtime.di

import android.app.Application
import androidx.room.Room
import com.umutcansahin.manageyourtime.data.local.PlanDao
import com.umutcansahin.manageyourtime.data.local.PlanDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

 /*   fun provideDatabase(application: Application): PlanDatabase {
        return Room.databaseBuilder(
            application,
            PlanDatabase::class.java,
            "plan_database"
        ).build()
    }

    fun providePlanDao(database: PlanDatabase): PlanDao {
        return database.planDao()
    }

    single { provideDatabase(androidApplication()) }
    single { providePlanDao(get()) }
*/


    single {
        Room.databaseBuilder(
            androidApplication(),
            PlanDatabase::class.java,
            "plan_database"
        )
            .build()
    }

    single {
        get<PlanDatabase>().planDao()
    }
}