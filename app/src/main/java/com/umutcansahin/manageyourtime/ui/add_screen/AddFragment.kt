package com.umutcansahin.manageyourtime.ui.add_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.databinding.FragmentAddBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {

    private val viewModel by viewModel<AddViewModel>()
    private val args: AddFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
        getInfoFromNavArgs()
    }

    private fun initView() {
        with(binding) {
            textInputTime.setEndIconOnClickListener {
                requireView().showSnackBar(getString(R.string.info_for_time))
            }
            imageButtonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observeData() {
        collectFlow(viewModel.addPlanState) {
            when (it) {
                is RoomResponse.Loading -> {}
                is RoomResponse.Success -> {
                    requireView().showSnackBar(getString(R.string.saved))
                    findNavController().popBackStack()
                }
                is RoomResponse.Error -> {
                    when (it.errorType) {
                        ErrorType.NAME_IS_NULL_ERROR ->
                            requireView().showSnackBar(getString(R.string.title_error))
                        ErrorType.TIME_IS_NULL_ERROR ->
                            requireView().showSnackBar(getString(R.string.time_error))
                        ErrorType.NAME_IS_BLANK_ERROR ->
                            requireView().showSnackBar(getString(R.string.title_error))
                        ErrorType.TIME_IS_BLANK_ERROR ->
                            requireView().showSnackBar(getString(R.string.time_error))
                        ErrorType.TIME_IS_EQUALS_ZERO_ERROR ->
                            requireView().showSnackBar(getString(R.string.time_zero_error))
                        ErrorType.ROOM_DEFAULT_ERROR ->
                            requireView().showSnackBar(getString(R.string.default_error))
                    }
                }
            }
        }
        collectFlow(viewModel.updatePlanState) {
            when (it) {
                is RoomResponse.Loading -> {}
                is RoomResponse.Success -> {
                    requireView().showSnackBar(getString((R.string.updated)))
                    findNavController().popBackStack()
                }
                is RoomResponse.Error -> {
                    when (it.errorType) {
                        ErrorType.NAME_IS_NULL_ERROR ->
                            requireView().showSnackBar(getString(R.string.title_error))
                        ErrorType.TIME_IS_NULL_ERROR ->
                            requireView().showSnackBar(getString(R.string.time_error))
                        ErrorType.NAME_IS_BLANK_ERROR ->
                            requireView().showSnackBar(getString(R.string.title_error))
                        ErrorType.TIME_IS_BLANK_ERROR ->
                            requireView().showSnackBar(getString(R.string.title_error))
                        ErrorType.TIME_IS_EQUALS_ZERO_ERROR ->
                            requireView().showSnackBar(getString(R.string.time_zero_error))
                        ErrorType.ROOM_DEFAULT_ERROR ->
                            requireView().showSnackBar(getString(R.string.default_error))
                    }
                }

            }
        }
    }

    private fun getInfoFromNavArgs() {
        val navType = args.navType
        val data = args.data
        when (navType) {
            NavType.ADD_NEW_ITEM -> addNewItem()
            NavType.UPDATE_ITEM -> updateItem(data)
        }
    }

    private fun addNewItem() {
        binding.buttonAddOrUpdatePlan.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val time = binding.editTextTime.text.toString()
            viewModel.addPlan(name = title, time = time)
        }
    }

    private fun updateItem(data: PlanEntity?) {
        with(binding) {
            editTextTime.setText(data!!.time.convertFromMillisecondToMinute())
            editTextTitle.setText(data.name)
            textViewInfo.text = requireContext().getString(R.string.update_plan_info)
            buttonAddOrUpdatePlan.text = requireContext().getText(R.string.update_plan)
            buttonAddOrUpdatePlan.setOnClickListener {
                val title = editTextTitle.text.toString()
                val time = binding.editTextTime.text.toString()
                val id = data.id
                viewModel.updatePlan(name = title, time = time, id = id)
            }
        }
    }
}