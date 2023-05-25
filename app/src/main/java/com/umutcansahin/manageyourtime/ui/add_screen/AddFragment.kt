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

    private fun observeData() {
        collectFlow(viewModel.addPlanState) {
            when (it) {
                is RoomResponse.Success -> {
                    requireContext().showToast(getString(R.string.succesful))
                    findNavController().popBackStack()
                }
                is RoomResponse.Error -> {
                    when (it.errorType) {
                        ErrorType.NAME_IS_NULL_ERROR ->
                            requireContext().showToast(getString(R.string.name_is_not_null))
                        ErrorType.TIME_IS_NULL_ERROR ->
                            requireContext().showToast(getString(R.string.time_is_not_null))
                        ErrorType.NAME_IS_BLANK_ERROR ->
                            requireContext().showToast(getString(R.string.name_is_not_empty))
                        ErrorType.TIME_IS_BLANK_ERROR ->
                            requireContext().showToast(getString(R.string.time_is_not_empty))
                        ErrorType.TIME_IS_EQUALS_ZERO_ERROR ->
                            requireContext().showToast(getString(R.string.time_is_not_equals_zero))
                        ErrorType.ROOM_DEFAULT_ERROR ->
                            requireContext().showToast(getString(R.string.please_try_again_later))
                    }
                }
                is RoomResponse.Loading -> {}
            }
        }
        collectFlow(viewModel.updatePlanState) {
            when (it) {
                is RoomResponse.Success -> {
                    requireContext().showToast(getString(R.string.succesful))
                    findNavController().popBackStack()
                }
                is RoomResponse.Error -> {
                    when (it.errorType) {
                        ErrorType.NAME_IS_NULL_ERROR ->
                            requireContext().showToast(getString(R.string.name_is_not_null))
                        ErrorType.TIME_IS_NULL_ERROR ->
                            requireContext().showToast(getString(R.string.time_is_not_null))
                        ErrorType.NAME_IS_BLANK_ERROR ->
                            requireContext().showToast(getString(R.string.name_is_not_empty))
                        ErrorType.TIME_IS_BLANK_ERROR ->
                            requireContext().showToast(getString(R.string.time_is_not_empty))
                        ErrorType.TIME_IS_EQUALS_ZERO_ERROR ->
                            requireContext().showToast(getString(R.string.time_is_not_equals_zero))
                        ErrorType.ROOM_DEFAULT_ERROR ->
                            requireContext().showToast(getString(R.string.please_try_again_later))
                    }
                }
                is RoomResponse.Loading -> {}
            }
        }
    }

    private fun initView() {
        with(binding) {
            textInputTime.setEndIconOnClickListener {
                requireContext().showToast(getString(R.string.infomation_toast_message))
            }
            imageButtonBack.setOnClickListener {
                findNavController().popBackStack()
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