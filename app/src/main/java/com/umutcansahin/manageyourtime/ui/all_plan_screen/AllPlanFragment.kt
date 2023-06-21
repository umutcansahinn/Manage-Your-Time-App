package com.umutcansahin.manageyourtime.ui.all_plan_screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.common.extensions.collectFlow
import com.umutcansahin.manageyourtime.common.extensions.showAlertDialog
import com.umutcansahin.manageyourtime.common.extensions.showSnackBar
import com.umutcansahin.manageyourtime.common.filter.Filter
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
        viewModel.filter(filter)
        getInfoFromArgs()
        observeData()
        initView()
    }

    private fun getInfoFromArgs() {
        args.filter?.let {
            filter = it
            viewModel.filter(filter)
        }
    }

    private fun observeData() {
        collectFlow(viewModel.getPlanEntityByFilter) {
            when (it) {
                is Resource.Loading ->
                    setViewVisibility(visibilityForProgressBar = View.VISIBLE)
                is Resource.Error -> {
                    setViewVisibility(visibilityForTextViewError = View.VISIBLE)
                    binding.textViewError.text = it.errorMessage
                }
                is Resource.Success -> {
                    if (it.data.isNotEmpty()) {
                        adapter.submitList(it.data)
                        setViewVisibility(visibilityForRecyclerview = View.VISIBLE)
                    } else {
                        setViewVisibility(visibilityForEmptyList = View.VISIBLE)
                    }
                }
            }
        }
        collectFlow(viewModel.deleteAllPlans) {
            when (it) {
                is RoomResponse.Success ->
                    requireView().showSnackBar(getString(R.string.deleted))
                is RoomResponse.Loading -> {}
                is RoomResponse.Error ->
                    requireView().showSnackBar(getString(R.string.default_error))
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

            if (isSearchVisible) {
                textInputSearch.visibility = View.VISIBLE
            } else {
                textInputSearch.visibility = View.GONE
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
                        viewModel.filter(filter)
                    } else {
                        viewModel.getPlanEntityBySearch(s.toString(),filter)
                    }
                }
            })
        }
    }

    private fun setViewVisibility(
        visibilityForProgressBar: Int = View.GONE,
        visibilityForTextViewError: Int = View.GONE,
        visibilityForRecyclerview: Int = View.GONE,
        visibilityForEmptyList: Int = View.GONE
    ) {
        with(binding) {
            progressBar.visibility = visibilityForProgressBar
            textViewError.visibility = visibilityForTextViewError
            recyclerView.visibility = visibilityForRecyclerview
            emptyList.constraint.visibility = visibilityForEmptyList

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