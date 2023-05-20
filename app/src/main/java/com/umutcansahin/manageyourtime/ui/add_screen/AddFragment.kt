package com.umutcansahin.manageyourtime.ui.add_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.databinding.FragmentAddBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {

    private val viewModel by viewModel<AddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun observeData() {
        this.collectFlow(viewModel.state) {
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
            buttonAddPlan.setOnClickListener {
                val title = binding.editTextTitle.text.toString()
                val time = binding.editTextTime.text.toString()
                viewModel.addPlan(name = title, time = time, context = requireContext())
            }
        }
    }
}