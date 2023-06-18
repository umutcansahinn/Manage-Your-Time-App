package com.umutcansahin.manageyourtime.ui.detail_plan_screen

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.common.extensions.HUNDRED
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.usecase.AddOrDeleteFromFavoriteUseCase
import com.umutcansahin.manageyourtime.domain.usecase.DeletePlanUseCase
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _resetState = MutableStateFlow(DetailState())
    val resetState = _resetState.asSharedFlow()

    var timerStartValue: Long = 0
    var timerPauseValue: Long = 0
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning: Boolean = false
    var isFavorite = false


    fun getPlanEntityById(entityId: Int) {
        viewModelScope.launch {
            getPlanEntityByIdUseCase(entityId).collect { entity ->
                _getEntityById.update { entity }
            }
        }
    }

    fun deletePlan(entity: PlanEntity) {
        viewModelScope.launch {
            deletePlanUseCase(entity).collect { response ->
                _deleteEntity.update { response }
            }
        }
    }

    fun addOrDeleteFromFavorite(entity: PlanEntity) {
        viewModelScope.launch {
            addOrDeleteFromFavoriteUseCase(entity).collect { response ->
                _addOrDeleteFromFavorite.update { response }
            }
        }
    }

    fun startTimer() {
        if (!isTimerRunning) {
            timer(timerPauseValue)
            isTimerRunning = true
        }
    }

    fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    fun resetTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
            timerPauseValue = 0
            isTimerRunning = false
        }
        _resetState.update { DetailState(textViewTime = timerStartValue, isTimeNullOrBlank = false) }
    }

    private fun timer(pauseTime: Long) {
        countDownTimer = object : CountDownTimer(
            timerStartValue - pauseTime,
            Long.HUNDRED
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _resetState.update {
                    DetailState(
                        textViewTime = millisUntilFinished,
                        isTimeNullOrBlank = false
                    )
                }
                timerPauseValue = timerStartValue - millisUntilFinished
            }

            override fun onFinish() {
                resetTimer()
                _resetState.update { DetailState(isTimeFinish = true) }
            }
        }.start()
    }
}