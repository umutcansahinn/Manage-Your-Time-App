package com.umutcansahin.manageyourtime.di

import com.umutcansahin.manageyourtime.data.repository.PlanRepositoryImpl
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    factory<PlanRepository> {
        PlanRepositoryImpl(
            planDao = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }
}