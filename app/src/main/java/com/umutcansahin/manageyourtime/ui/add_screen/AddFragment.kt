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
        this.collectFlow(viewModel.addPlanState) {
            when (it) {
                is Resource.Success -> {
                    requireContext().showToast(it.data)
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    requireContext().showToast(it.errorMessage ?: String.EMPTY)
                }
                is Resource.Loading -> {}
            }
        }
        this.collectFlow(viewModel.updatePlanState) {
            when (it) {
                is Resource.Success -> {
                    requireContext().showToast(it.data)
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    requireContext().showToast(it.errorMessage ?: String.EMPTY)
                }
                is Resource.Loading -> {}
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
            viewModel.addPlan(name = title, time = time, context = requireContext())
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
                viewModel.updatePlan(name = title, time = time, id = id, context = requireContext())
            }
        }
    }
}