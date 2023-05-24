package com.umutcansahin.manageyourtime.di

import com.umutcansahin.manageyourtime.data.usecase.*
import com.umutcansahin.manageyourtime.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {

    factory<AddPlanUseCase> { AddPlanUseCaseImpl(planRepository = get()) }

    factory<DeleteAllPlanEntityUseCase> { DeleteAllPlanEntityUseCaseImpl(planRepository = get()) }

    factory<DeletePlanUseCase> { DeletePlanUseCaseImpl(planRepository = get()) }

    factory<GetAllPlanEntityUseCase> { GetAllPlanEntityUseCaseImpl(planRepository = get()) }

    factory<UpdatePlanUseCase> { UpdatePlanUseCaseImpl(planRepository = get()) }

    factory<GetPlanEntityByIdUseCase> { GetPlanEntityByIdUseCaseImpl(planRepository = get()) }

    factory<AddOrDeleteFromFavoriteUseCase> { AddOrDeleteFromFavoriteUseCaseImpl(planRepository = get()) }

    factory<GetPlanEntityBySearchUseCase> { GetPlanEntityBySearchUseCaseImpl(planRepository = get()) }

    factory<GetPlanEntityByFilterUseCase> { GetPlanEntityByFilterUseCaseImpl(planRepository = get()) }

}