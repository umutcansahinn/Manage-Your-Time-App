package com.umutcansahin.manageyourtime.ui.count_down_timer_screen

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.databinding.FragmentCountDownTimerBinding

class CountDownTimerFragment :
    BaseFragment<FragmentCountDownTimerBinding>(FragmentCountDownTimerBinding::inflate) {


    private var timerStartValue: Long = 0
    private var timerPauseValue: Long = 0
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        with(binding) {

            editTextTime.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrBlank()) {
                        timerStartValue = s.toString().convertToMillisecond()
                        textViewTime.text =
                            (timerStartValue - timerPauseValue).convertToMinuteAndSecond()
                    } else {
                        val empty = String.START_TIME.convertToMillisecond()
                        textViewTime.text = empty.convertToMinuteAndSecond()
                    }
                }
            })
            editTextTime.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrBlank()) {
                    textInputTime.error = getString(R.string.time_can_not_be_empty)
                } else {
                    textInputTime.error = null
                }
            }
            textInputTime.setEndIconOnClickListener {
                requireView().showSnackBar(getString(R.string.info_for_time))
            }
            imageButtonBack.setOnClickListener {
                findNavController().popBackStack()
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
            if (!binding.editTextTime.text.isNullOrBlank()) {
                timer(timerPauseValue)
                isTimerRunning = true
                binding.textInputTime.isEnabled = false
            }
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
            timerPauseValue = 0
            isTimerRunning = false
        }
        binding.textViewTime.text = timerStartValue.convertToMinuteAndSecond()
        binding.textInputTime.isEnabled = true
        binding.textInputTime.isFocusable = true
    }

    private fun timer(pauseTime: Long) {
        countDownTimer = object : CountDownTimer(
            timerStartValue - pauseTime,
            Long.HUNDRED
        ) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textViewTime.text = millisUntilFinished.convertToMinuteAndSecond()
                timerPauseValue = timerStartValue - millisUntilFinished
            }

            override fun onFinish() {
                resetTimer()
                binding.textViewTime.text = requireContext().getString(R.string.done)
            }
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