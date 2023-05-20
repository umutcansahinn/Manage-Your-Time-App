package com.umutcansahin.manageyourtime.ui.all_plan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.usecase.GetAllPlanEntityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllPlanViewModel(
    private val getAllPlanEntityUseCase: GetAllPlanEntityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<PlanEntity>>>(Resource.Loading)
    val state = _state.asStateFlow()

    init {
        getAllPlan()
    }

    private fun getAllPlan() {
        viewModelScope.launch {
            getAllPlanEntityUseCase().collect {
                _state.value = it
            }
        }
    }
}