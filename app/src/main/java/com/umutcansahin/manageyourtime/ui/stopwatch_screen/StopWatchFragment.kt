package com.umutcansahin.manageyourtime.ui.stopwatch_screen

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.THOUSAND
import com.umutcansahin.manageyourtime.common.convertToMinuteAndSecond
import com.umutcansahin.manageyourtime.databinding.FragmentStopWatchBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StopWatchFragment :
    BaseFragment<FragmentStopWatchBinding>(FragmentStopWatchBinding::inflate) {

    private val viewModel by activityViewModel<StopWatchViewModel>()
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewTime.text = viewModel.timerStartValue.convertToMinuteAndSecond()
        initUi()
    }

    private fun initUi() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                findNavController().navigate(R.id.action_stopWatchFragment_to_homeFragment)
            }
            imageButtonStartTimer.setOnClickListener {
                startTimer()
            }
            imageButtonStopTimer.setOnClickListener {
                pauseTimer()
            }
            imageButtonResetTimer.setOnClickListener {
                resetTimer()
            }
        }
    }

    private fun startTimer() {
        if (!isTimerRunning) {
            timer()
            isTimerRunning = true
        }
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    private fun resetTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
            isTimerRunning = false
            viewModel.timerStartValue = 0
        }
        binding.textViewTime.text = viewModel.timerStartValue.convertToMinuteAndSecond()
    }

    private fun timer() {
        countDownTimer = object : CountDownTimer(
            Long.MAX_VALUE,
            Long.THOUSAND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.timerStartValue += 1000
                binding.textViewTime.text = viewModel.timerStartValue.convertToMinuteAndSecond()
            }

            override fun onFinish() {}
        }.start()
    }

    override fun onStop() {
        pauseTimer()
        super.onStop()
    }

    override fun onDestroyView() {
        countDownTimer?.cancel()
        super.onDestroyView()
    }
}