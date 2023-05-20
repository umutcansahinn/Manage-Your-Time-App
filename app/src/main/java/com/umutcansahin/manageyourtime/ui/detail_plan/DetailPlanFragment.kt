package com.umutcansahin.manageyourtime.ui.detail_plan

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.databinding.FragmentDetailPlanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPlanFragment :
    BaseFragment<FragmentDetailPlanBinding>(FragmentDetailPlanBinding::inflate) {

    private val viewModel by viewModel<DetailPlanViewModel>()
    private val args: DetailPlanFragmentArgs by navArgs()

    private var timerStartValue: Long = 0
    private var timerPauseValue: Long = 0
    private var countDownTimer: CountDownTimer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun initView() {
        val entityId = args.entityId
        viewModel.getPlanEntityById(entityId)
    }

    private fun observeData() {
        this.collectFlow(viewModel.getEntityById) {
            when (it) {
                is Resource.Success -> {
                    setViewVisibility(visibilityForConstraintLayout = View.VISIBLE)
                    setUi(it.data)
                }
                is Resource.Loading -> {
                    setViewVisibility(visibilityForProgressBar = View.VISIBLE)
                }
                is Resource.Error -> {
                    setViewVisibility(visibilityForTextViewError = View.VISIBLE)
                    binding.textViewError.text = it.errorMessage
                }
            }
        }
        this.collectFlow(viewModel.deleteEntity) {
            when (it) {
                is Resource.Success -> {
                    findNavController().popBackStack()
                }
                is Resource.Loading -> {
                    setViewVisibility(visibilityForProgressBar = View.VISIBLE)
                }
                is Resource.Error -> {
                    setViewVisibility(visibilityForTextViewError = View.VISIBLE)
                    binding.textViewError.text = it.errorMessage
                }
            }
        }
    }

    private fun setViewVisibility(
        visibilityForProgressBar: Int = View.GONE,
        visibilityForTextViewError: Int = View.GONE,
        visibilityForConstraintLayout: Int = View.GONE
    ) {
        with(binding) {
            progressBar.visibility = visibilityForProgressBar
            textViewError.visibility = visibilityForTextViewError
            constraintLayout.visibility = visibilityForConstraintLayout
        }
    }

    private fun setUi(entity: PlanEntity) {
        timerStartValue = entity.time
        with(binding) {
            textViewTitle.text = entity.name
            textViewTime.text = (timerStartValue - timerPauseValue).convertToMinuteAndSecond()

            buttonUpdate.setOnClickListener {

            }
            buttonDelete.setOnClickListener {
                viewModel.deletePlan(entity)
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
        timer(timerPauseValue)
    }


    private fun pauseTimer() {
        countDownTimer?.cancel()
    }

    private fun timer(pauseTime: Long) {
        countDownTimer = object : CountDownTimer(
            timerStartValue - pauseTime,
            Long.THOUSAND
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


    private fun resetTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
            timerPauseValue = 0
        }
        binding.textViewTime.text = timerStartValue.convertToMinuteAndSecond()
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    override fun onStop() {
        super.onStop()
        pauseTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}