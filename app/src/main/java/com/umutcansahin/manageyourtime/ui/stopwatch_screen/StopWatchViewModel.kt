package com.umutcansahin.manageyourtime.ui.stopwatch_screen

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.umutcansahin.manageyourtime.common.extensions.THOUSAND
import com.umutcansahin.manageyourtime.common.extensions.convertToMinuteAndSecond
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class StopWatchViewModel : ViewModel() {
    private var timerStartValue: Long = 0
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning: Boolean = false

    private val _timer = MutableStateFlow(timerStartValue.convertToMinuteAndSecond())
    val timer = _timer.asSharedFlow()

    fun startTimer() {
        if (!isTimerRunning) {
            timer()
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
            isTimerRunning = false
            timerStartValue = 0
            _timer.value = timerStartValue.convertToMinuteAndSecond()
        }
    }

    private fun timer() {
        countDownTimer = object : CountDownTimer(
            Long.MAX_VALUE,
            Long.THOUSAND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                timerStartValue += Long.THOUSAND
                _timer.value = timerStartValue.convertToMinuteAndSecond()
            }

            override fun onFinish() {}
        }.start()
    }
}
