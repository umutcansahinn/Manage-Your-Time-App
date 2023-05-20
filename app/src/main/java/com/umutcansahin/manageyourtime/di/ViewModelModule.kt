package com.umutcansahin.manageyourtime.di

import com.umutcansahin.manageyourtime.ui.add_screen.AddViewModel
import com.umutcansahin.manageyourtime.ui.all_plan_screen.AllPlanViewModel
import com.umutcansahin.manageyourtime.ui.detail_plan.DetailPlanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AddViewModel(addPlanUseCase = get()) }
    viewModel { AllPlanViewModel(getAllPlanEntityUseCase = get()) }
    viewModel { DetailPlanViewModel(getPlanEntityByIdUseCase = get(), deletePlanUseCase = get()) }
}