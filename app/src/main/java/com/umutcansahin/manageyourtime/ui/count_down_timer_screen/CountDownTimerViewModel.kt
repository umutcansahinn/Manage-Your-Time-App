package com.umutcansahin.manageyourtime.ui.count_down_timer_screen

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.umutcansahin.manageyourtime.common.extensions.HUNDRED
import com.umutcansahin.manageyourtime.common.extensions.ZERO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

class CountDownTimerViewModel : ViewModel() {

    var timerStartValue: Long = 0
    var timerPauseValue: Long = 0
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning: Boolean = false

    private val _countDownState = MutableStateFlow(CountDownState())
    val state = _countDownState.asSharedFlow()

    fun startTimer(time: String?) {
        if (!isTimerRunning) {
            val firstCondition = time.isNullOrBlank().not()
            val secondCondition = time.equals("0").not()

            if (firstCondition && secondCondition) {
                timer(timerPauseValue)
                isTimerRunning = true
            } else {
                _countDownState.update { CountDownState(isTimeNullOrBlank = true) }
            }
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
        _countDownState.update {
            CountDownState(
                timerStartValue = timerStartValue,
                textInputTimeIsEnable = true,
                textInputTimeIsFocusable = true,
                isTimeNullOrBlank = false
            )
        }
    }

    private fun timer(pauseTime: Long) {
        countDownTimer = object : CountDownTimer(
            timerStartValue - pauseTime,
            Long.HUNDRED
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _countDownState.update {
                    CountDownState(
                        timerStartValue = millisUntilFinished,
                        textInputTimeIsEnable = false,
                        textInputTimeIsFocusable = false,
                        isTimeNullOrBlank = false
                    )
                }
                timerPauseValue = timerStartValue - millisUntilFinished
            }

            override fun onFinish() {
                resetTimer()
                _countDownState.update { CountDownState(isTimeFinish = true) }
            }
        }.start()
    }
}

data class CountDownState(
    val timerStartValue: Long = Long.ZERO,
    val textInputTimeIsEnable: Boolean = true,
    val textInputTimeIsFocusable: Boolean = true,
    val isTimeNullOrBlank: Boolean = false,
    val isTimeFinish: Boolean = false
)