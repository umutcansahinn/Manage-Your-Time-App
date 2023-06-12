package com.umutcansahin.manageyourtime.ui.stopwatch_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.extensions.collectFlow
import com.umutcansahin.manageyourtime.databinding.FragmentStopWatchBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StopWatchFragment :
    BaseFragment<FragmentStopWatchBinding>(FragmentStopWatchBinding::inflate) {

    private val viewModel by activityViewModel<StopWatchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initUi()
    }

    private fun observeData() {
        collectFlow(viewModel.timer) {
            binding.textViewTime.text = it
        }
    }

    private fun initUi() {
        with(binding) {

            imageButtonBack.setOnClickListener {
                findNavController().navigate(R.id.action_stopWatchFragment_to_homeFragment)
            }
            imageButtonStartTimer.setOnClickListener {
                viewModel.startTimer()
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