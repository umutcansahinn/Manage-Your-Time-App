package com.umutcansahin.manageyourtime.ui.detail_plan_screen

import android.os.Bundle
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
                is RoomResponse.Loading -> {}
                is RoomResponse.Error -> requireView().showSnackBar(getString(R.string.default_error))
                is RoomResponse.Success -> {
                    requireView().showSnackBar(getString(R.string.deleted))
                    findNavController().popBackStack()
                }
            }
        }
        collectFlow(viewModel.addOrDeleteFromFavorite) {
            when (it) {
                is RoomResponse.Loading -> {}
                is RoomResponse.Error -> requireView().showSnackBar(getString(R.string.default_error))
                is RoomResponse.Success -> {}
            }
        }
        collectFlow(viewModel.state2) {state->
            with(binding) {
                textViewTime.text = state.textViewTime
                if (state.isTimeFinish) textViewTime.text = requireContext().getString(R.string.done)
                if (state.isTimeNullOrBlank) requireView().showSnackBar(getString(R.string.info_for_time))
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
        viewModel.timerStartValue = entity.time
        with(binding) {
            textViewTitle.text = entity.name
            textViewTime.text =
                (viewModel.timerStartValue - viewModel.timerPauseValue).convertToMinuteAndSecond()
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
                viewModel.startTimer()
            }
            imageButtonStopTimer.setOnClickListener {
                viewModel.pauseTimer()
            }
            imageButtonResetTimer.setOnClickListener {
                viewModel.resetTimer()
            }
            imageButtonFavorite.setOnClickListener {
                viewModel.isFavorite = viewModel.isFavorite.not()
                viewModel.addOrDeleteFromFavorite(entity.copy(favorite = viewModel.isFavorite))
            }
        }
    }

    override fun onStop() {
        viewModel.pauseTimer()
        super.onStop()

    }
}