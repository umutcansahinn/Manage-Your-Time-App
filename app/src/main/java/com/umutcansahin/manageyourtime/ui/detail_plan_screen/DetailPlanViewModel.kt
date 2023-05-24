package com.umutcansahin.manageyourtime.ui.detail_plan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.usecase.AddOrDeleteFromFavoriteUseCase
import com.umutcansahin.manageyourtime.domain.usecase.DeletePlanUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailPlanViewModel(
    private val getPlanEntityByIdUseCase: GetPlanEntityByIdUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val addOrDeleteFromFavoriteUseCase: AddOrDeleteFromFavoriteUseCase
) : ViewModel() {

    private val _getEntityById = MutableStateFlow<Resource<PlanEntity>>(Resource.Loading)
    val getEntityById = _getEntityById.asStateFlow()

    private val _deleteEntity = MutableStateFlow<RoomResponse>(RoomResponse.Loading)
    val deleteEntity = _deleteEntity.asStateFlow()

    private val _addOrDeleteFromFavorite = MutableStateFlow<RoomResponse>(RoomResponse.Loading)
    val addOrDeleteFromFavorite = _addOrDeleteFromFavorite.asStateFlow()


    fun getPlanEntityById(entityId: Int) {
        viewModelScope.launch {
            getPlanEntityByIdUseCase(entityId).collect {
                _getEntityById.value = it
            }
        }
    }

    fun deletePlan(entity: PlanEntity) {
        viewModelScope.launch {
            deletePlanUseCase(entity).collect {
                _deleteEntity.value = it
            }
        }
    }

    fun addOrDeleteFromFavorite(entity: PlanEntity) {
        viewModelScope.launch {
            addOrDeleteFromFavoriteUseCase(entity).collect {
                _addOrDeleteFromFavorite.value = it
            }
        }
    }
}


