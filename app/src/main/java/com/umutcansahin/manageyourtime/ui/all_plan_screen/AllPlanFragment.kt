package com.umutcansahin.manageyourtime.ui.all_plan_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.common.collectFlow
import com.umutcansahin.manageyourtime.common.showAlertDialog
import com.umutcansahin.manageyourtime.databinding.FragmentAllPlanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllPlanFragment : BaseFragment<FragmentAllPlanBinding>(FragmentAllPlanBinding::inflate) {

    private val viewModel by viewModel<AllPlanViewModel>()
    private val adapter = PlanAdapter(::itemSetClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllPlan()
        initView()
        observeData()
    }

    private fun observeData() {
        collectFlow(viewModel.state) {
            when (it) {
                is Resource.Loading -> {
                    setViewVisibility(visibilityForProgressBar = View.VISIBLE)
                }
                is Resource.Error -> {
                    setViewVisibility(visibilityForTextViewError = View.VISIBLE)
                }
                is Resource.Success -> {
                    if (it.data.isNotEmpty()) {
                        adapter.submitList(it.data)
                        setViewVisibility(visibilityForRecyclerview = View.VISIBLE)
                    } else {
                        setViewVisibility(visibilityForTextViewEmptyList = View.VISIBLE)
                    }
                }
            }
        }
        collectFlow(viewModel.deleteAllPlans) {
            when (it) {
                is RoomResponse.Success -> {}
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

    private fun initView() {
        with(binding) {
            imageButtonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonDeleteAll.setOnClickListener {
                requireContext().showAlertDialog(
                    getString(R.string.alert),
                    getString(R.string.alert_dialog_delete_all_message)
                ) {
                    viewModel.deleteAllPlans()
                }
            }
            recyclerView.adapter = adapter
        }
    }

    private fun setViewVisibility(
        visibilityForProgressBar: Int = View.GONE,
        visibilityForTextViewError: Int = View.GONE,
        visibilityForRecyclerview: Int = View.GONE,
        visibilityForTextViewEmptyList: Int = View.GONE
    ) {
        with(binding) {
            progressBar.visibility = visibilityForProgressBar
            textViewError.visibility = visibilityForTextViewError
            recyclerView.visibility = visibilityForRecyclerview
            textViewEmptyList.visibility = visibilityForTextViewEmptyList
        }
    }

    private fun itemSetClick(entityId: Int) {
        findNavController().navigate(
            AllPlanFragmentDirections.actionAllPlanFragmentToDetailPlanFragment(
                entityId = entityId
            )
        )
    }
}