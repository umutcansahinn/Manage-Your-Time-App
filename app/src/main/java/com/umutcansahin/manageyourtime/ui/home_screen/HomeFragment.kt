package com.umutcansahin.manageyourtime.ui.home_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            addScreen.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
            }
            allListScreen.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllPlanFragment())
            }
        }
    }
}