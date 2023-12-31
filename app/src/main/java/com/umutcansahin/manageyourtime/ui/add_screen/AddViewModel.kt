package com.umutcansahin.manageyourtime.ui.add_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.domain.usecase.AddPlanUseCase
import com.umutcansahin.manageyourtime.domain.usecase.UpdatePlanUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddViewModel(
    private val addPlanUseCase: AddPlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase
) : ViewModel() {

    private val _addPlanState = MutableSharedFlow<RoomResponse>()
    val addPlanState = _addPlanState.asSharedFlow()

    private val _updatePlanState = MutableSharedFlow<RoomResponse>()
    val updatePlanState = _updatePlanState.asSharedFlow()

    fun addPlan(name: String?, time: String?) {
        viewModelScope.launch {
            val result = addPlanUseCase(name, time)
            _addPlanState.emit(result)
        }
    }

    fun updatePlan(name: String?, time: String?, id: Int) {
        viewModelScope.launch {
            updatePlanUseCase(name, time, id).collect {
                _updatePlanState.emit(it)
            }
        }
    }
}