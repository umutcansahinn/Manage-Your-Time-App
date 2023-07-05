package com.umutcansahin.manageyourtime.di

import com.umutcansahin.manageyourtime.data.usecase.AddOrDeleteFromFavoriteUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.AddPlanUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.DeleteAllPlanEntityUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.DeletePlanUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.GetAllPlanEntityUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.GetPlanEntityByFilterUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.GetPlanEntityByIdUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.GetPlanEntityBySearchUseCaseImpl
import com.umutcansahin.manageyourtime.data.usecase.UpdatePlanUseCaseImpl
import com.umutcansahin.manageyourtime.domain.usecase.AddOrDeleteFromFavoriteUseCase
import com.umutcansahin.manageyourtime.domain.usecase.AddPlanUseCase
import com.umutcansahin.manageyourtime.domain.usecase.DeleteAllPlanEntityUseCase
import com.umutcansahin.manageyourtime.domain.usecase.DeletePlanUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetAllPlanEntityUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByFilterUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByIdUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityBySearchUseCase
import com.umutcansahin.manageyourtime.domain.usecase.UpdatePlanUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {

    factory<AddPlanUseCase> { AddPlanUseCaseImpl(planRepository = get()) }

    factory<DeleteAllPlanEntityUseCase> { DeleteAllPlanEntityUseCaseImpl(planRepository = get()) }

    factory<DeletePlanUseCase> { DeletePlanUseCaseImpl(planRepository = get()) }

    factory<GetAllPlanEntityUseCase> { GetAllPlanEntityUseCaseImpl(planRepository = get()) }

    factory<UpdatePlanUseCase> { UpdatePlanUseCaseImpl(planRepository = get()) }

    factory<GetPlanEntityByIdUseCase> { GetPlanEntityByIdUseCaseImpl(planRepository = get()) }

    factory<AddOrDeleteFromFavoriteUseCase> { AddOrDeleteFromFavoriteUseCaseImpl(planRepository = get()) }

    factory<GetPlanEntityBySearchUseCase> {
        GetPlanEntityBySearchUseCaseImpl(
            planRepository = get(),
            get(named("defaultDispatcher"))
        )
    }

    factory<GetPlanEntityByFilterUseCase> {
        GetPlanEntityByFilterUseCaseImpl(
            planRepository = get(), defaultDispatcher = get(
                named("defaultDispatcher")
            )
        )
    }
}