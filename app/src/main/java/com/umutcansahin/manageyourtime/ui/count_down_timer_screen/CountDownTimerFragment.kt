package com.umutcansahin.manageyourtime.ui.count_down_timer_screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.extensions.*
import com.umutcansahin.manageyourtime.databinding.FragmentCountDownTimerBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CountDownTimerFragment :
    BaseFragment<FragmentCountDownTimerBinding>(FragmentCountDownTimerBinding::inflate) {

    private val viewModel by activityViewModel<CountDownTimerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewTime.text = viewModel.timerStartValue.convertToMinuteAndSecond()
        binding.editTextTime.setText(viewModel.timerStartValue.convertFromMillisecondToMinute())
        observeData()
        initUi()
    }

    private fun observeData() {
        collectFlow(viewModel.state) { state ->
            with(binding) {
                textViewTime.text = state.textViewTime
                textInputTime.isFocusable = state.textInputTimeIsFocusable
                textInputTime.isEnabled = state.textInputTimeIsEnable
                if (state.isTimeFinish) textViewTime.text = requireContext().getString(R.string.done)
                if (state.isTimeNullOrBlank) requireView().showSnackBar(getString(R.string.info_for_time))
            }
        }
    }

    private fun initUi() {
        with(binding) {

            editTextTime.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s.isNullOrBlank().not()) {
                        viewModel.timerStartValue = s.toString().convertToMillisecond()
                        textViewTime.text =
                            (viewModel.timerStartValue - viewModel.timerPauseValue).convertToMinuteAndSecond()
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
                viewModel.startTimer(editTextTime.text.toString())
            }
            imageButtonStopTimer.setOnClickListener {
                viewModel.pauseTimer()
            }
            imageButtonResetTimer.setOnClickListener {
                viewModel.resetTimer()
            }
        }
    }


    override fun onStop() {
        viewModel.pauseTimer()
        super.onStop()
    }
}