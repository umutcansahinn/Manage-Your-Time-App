package com.umutcansahin.manageyourtime.ui.detail_plan_screen

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
    private var isTimerRunning: Boolean = false

    private var isFavorite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoFromArgs()
        observeData()
    }

    private fun getInfoFromArgs() {
        val entityId = args.entityId
        viewModel.getPlanEntityById(entityId)
    }

    private fun observeData() {
        collectFlow(viewModel.getEntityById) {
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
        collectFlow(viewModel.deleteEntity) {
            when (it) {
                is RoomResponse.Success -> {
                    setViewVisibility(visibilityForConstraintLayout = View.VISIBLE)
                    findNavController().popBackStack()
                }
                is RoomResponse.Loading -> {
                    setViewVisibility(visibilityForProgressBar = View.VISIBLE)
                }
                is RoomResponse.Error -> {
                    setViewVisibility(visibilityForTextViewError = View.VISIBLE)
                    binding.textViewError.text =
                        requireContext().getString(R.string.please_try_again_later)
                }
            }
        }
        collectFlow(viewModel.addOrDeleteFromFavorite) {
            when (it) {
                is RoomResponse.Success -> {
                    setViewVisibility(visibilityForConstraintLayout = View.VISIBLE)
                }
                is RoomResponse.Loading -> {
                    setViewVisibility(visibilityForProgressBar = View.VISIBLE)
                }
                is RoomResponse.Error -> {
                    setViewVisibility(visibilityForTextViewError = View.VISIBLE)
                    binding.textViewError.text =
                        requireContext().getString(R.string.please_try_again_later)
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
            if (entity.favorite) {
                imageButtonFavorite.setImageResource(R.drawable.baseline_star_24)
            } else {
                imageButtonFavorite.setImageResource(R.drawable.baseline_star_border_24)
            }
            buttonUpdate.setOnClickListener {
                findNavController().navigate(
                    DetailPlanFragmentDirections.actionDetailPlanFragmentToAddFragment(
                        navType = NavType.UPDATE_ITEM,
                        data = entity
                    )
                )
            }
            buttonDelete.setOnClickListener {
                requireContext().showAlertDialog(
                    getString(R.string.alert),
                    getString(R.string.do_you_want_to_delete_this_plan)
                ) {
                    viewModel.deletePlan(entity)
                }
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
            imageButtonFavorite.setOnClickListener {
                isFavorite = !isFavorite
                viewModel.addOrDeleteFromFavorite(entity.copy(favorite = isFavorite))
            }
        }
    }

    private fun startTimer() {
        if (!isTimerRunning) {
            timer(timerPauseValue)
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
            timerPauseValue = 0
            isTimerRunning = false
        }
        binding.textViewTime.text = timerStartValue.convertToMinuteAndSecond()
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


    override fun onStop() {
        pauseTimer()
        super.onStop()

    }

    override fun onDestroyView() {
        countDownTimer?.cancel()
        super.onDestroyView()
    }
}