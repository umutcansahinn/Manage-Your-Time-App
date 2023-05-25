package com.umutcansahin.manageyourtime.ui.all_plan_screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.databinding.FragmentAllPlanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllPlanFragment : BaseFragment<FragmentAllPlanBinding>(FragmentAllPlanBinding::inflate) {

    private val viewModel by viewModel<AllPlanViewModel>()
    private val adapter = PlanAdapter(::itemSetClick)

    private var isSearchVisible = false
    private var filter = Filter()
    private val args: AllPlanFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoFromArgs()
        initView()
        observeData()
    }

    private fun getInfoFromArgs() {
        args.filter?.let {
            filter = it
            viewModel.filter(filter)
        }
    }

    private fun observeData() {
        collectFlow(viewModel.getAllPlanEntity) {
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
                is RoomResponse.Success -> {
                    setViewVisibility(visibilityForTextViewEmptyList = View.VISIBLE)
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
        collectFlow(viewModel.getPlanEntityBySearch) {
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
    }

    private fun initView() {
        with(binding) {
            recyclerView.adapter = adapter

            imageButtonBack.setOnClickListener {
                findNavController().navigate(AllPlanFragmentDirections.actionAllPlanFragmentToHomeFragment())
            }
            buttonDeleteAll.setOnClickListener {
                requireContext().showAlertDialog(
                    getString(R.string.alert),
                    getString(R.string.alert_dialog_delete_all_message)
                ) {
                    viewModel.deleteAllPlans()
                }
            }

            buttonFilter.setOnClickListener {
                findNavController().navigate(
                    AllPlanFragmentDirections.actionAllPlanFragmentToFilterFragment(
                        filter = filter
                    )
                )
            }

            buttonSearch.setOnClickListener {
                isSearchVisible = !isSearchVisible
                if (isSearchVisible) {
                    textInputSearch.visibility = View.VISIBLE
                } else {
                    textInputSearch.visibility = View.GONE
                }
            }

            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s == null || s.toString().isEmpty()) {
                        viewModel.getAllPlan()
                    } else {
                        viewModel.getPlanEntityBySearch(s.toString())
                    }
                }
            })
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