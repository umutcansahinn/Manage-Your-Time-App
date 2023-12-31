package com.umutcansahin.manageyourtime.ui.all_plan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.common.filter.Filter
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.usecase.DeleteAllPlanEntityUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByFilterUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityBySearchUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllPlanViewModel(
    private val deleteAllPlanEntityUseCase: DeleteAllPlanEntityUseCase,
    private val getPlanEntityBySearchUseCase: GetPlanEntityBySearchUseCase,
    private val getPlanEntityByFilterUseCase: GetPlanEntityByFilterUseCase
) : ViewModel() {

    private val _getPlanEntityByFilter =
        MutableStateFlow<Resource<List<PlanEntity>>>(Resource.Loading)
    val getPlanEntityByFilter = _getPlanEntityByFilter.asStateFlow()

    private val _deleteAllPlans = MutableSharedFlow<RoomResponse>()
    val deleteAllPlans = _deleteAllPlans.asSharedFlow()


    fun deleteAllPlans() {
        viewModelScope.launch {
            deleteAllPlanEntityUseCase().collect { response ->
                _deleteAllPlans.emit(response)
            }
        }
    }

    fun getPlanEntityBySearch(search: String, filter: Filter) {
        viewModelScope.launch {
            getPlanEntityBySearchUseCase(search, filter).collect { list ->
                _getPlanEntityByFilter.update { list }
            }
        }
    }

    fun filter(filter: Filter) {
        viewModelScope.launch {
            getPlanEntityByFilterUseCase(
                sortedBy = filter.sortedBy,
                favoriteType = filter.favoriteType,
                startTime = filter.startTime,
                endTime = filter.endTime
            ).collect { list ->
                _getPlanEntityByFilter.update { list }
            }
        }
    }
}