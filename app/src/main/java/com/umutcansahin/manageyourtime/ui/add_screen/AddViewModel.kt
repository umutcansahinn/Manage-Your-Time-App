package com.umutcansahin.manageyourtime.ui.add_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.domain.usecase.AddPlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddViewModel(
    private val addPlanUseCase: AddPlanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<String>>(Resource.Loading)
    val state = _state.asSharedFlow()

    fun addPlan(name: String?, time: String?, context: Context) {
        viewModelScope.launch {
            addPlanUseCase(name, time, context).collect {
                _state.value = it
            }
        }
    }
}