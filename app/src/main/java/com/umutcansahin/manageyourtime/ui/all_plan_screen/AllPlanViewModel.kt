package com.umutcansahin.manageyourtime.ui.all_plan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.usecase.DeleteAllPlanEntityUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetAllPlanEntityUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityBySearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllPlanViewModel(
    private val getAllPlanEntityUseCase: GetAllPlanEntityUseCase,
    private val deleteAllPlanEntityUseCase: DeleteAllPlanEntityUseCase,
    private val getPlanEntityBySearchUseCase: GetPlanEntityBySearchUseCase
) : ViewModel() {

    private val _getAllPlanEntity = MutableStateFlow<Resource<List<PlanEntity>>>(Resource.Loading)
    val getAllPlanEntity = _getAllPlanEntity.asStateFlow()

    private val _deleteAllPlans = MutableStateFlow<RoomResponse>(RoomResponse.Loading)
    val deleteAllPlans = _deleteAllPlans.asStateFlow()


    private val _getPlanEntityBySearch = MutableStateFlow<Resource<List<PlanEntity>>>(Resource.Loading)
    val getPlanEntityBySearch = _getPlanEntityBySearch.asStateFlow()



    fun getAllPlan() {
        viewModelScope.launch {
            getAllPlanEntityUseCase().collect {
                _getAllPlanEntity.value = it
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

    fun getPlanEntityBySearch(search: String) {
        viewModelScope.launch {
            getPlanEntityBySearchUseCase(search).collect {
                _getPlanEntityBySearch.value = it
            }
        }
    }
}