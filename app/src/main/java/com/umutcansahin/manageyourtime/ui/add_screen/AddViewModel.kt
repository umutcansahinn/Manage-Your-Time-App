package com.umutcansahin.manageyourtime.ui.add_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.domain.usecase.AddPlanUseCase
import com.umutcansahin.manageyourtime.domain.usecase.UpdatePlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddViewModel(
    private val addPlanUseCase: AddPlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase
) : ViewModel() {

    private val _addPlanState = MutableStateFlow<Resource<String>>(Resource.Loading)
    val addPlanState = _addPlanState.asSharedFlow()

    private val _updatePlanState = MutableStateFlow<Resource<String>>(Resource.Loading)
    val updatePlanState = _updatePlanState.asSharedFlow()

    fun addPlan(name: String?, time: String?, context: Context) {
        viewModelScope.launch {
            addPlanUseCase(name, time, context).collect {
                _addPlanState.value = it
            }
        }
    }

    fun updatePlan(name: String?,time: String?,id: Int,context: Context) {
        viewModelScope.launch {
            updatePlanUseCase(name,time,id,context).collect {
                _updatePlanState.value = it
            }
        }
    }
}