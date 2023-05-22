package com.umutcansahin.manageyourtime.ui.all_plan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.usecase.DeleteAllPlanEntityUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetAllPlanEntityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllPlanViewModel(
    private val getAllPlanEntityUseCase: GetAllPlanEntityUseCase,
    private val deleteAllPlanEntityUseCase: DeleteAllPlanEntityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<PlanEntity>>>(Resource.Loading)
    val state = _state.asStateFlow()

    private val _deleteAllPlans = MutableStateFlow<RoomResponse>(RoomResponse.Loading)
    val deleteAllPlans = _deleteAllPlans.asStateFlow()

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

    fun deleteAllPlans() {
        viewModelScope.launch {
            deleteAllPlanEntityUseCase().collect {
                _deleteAllPlans.value = it
            }
        }
    }
}