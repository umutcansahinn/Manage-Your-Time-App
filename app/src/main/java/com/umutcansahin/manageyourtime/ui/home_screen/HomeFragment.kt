package com.umutcansahin.manageyourtime.ui.home_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.NavType
import com.umutcansahin.manageyourtime.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            addScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAddFragment(
                        navType = NavType.ADD_NEW_ITEM,
                        data = null
                    )
                )
            }
            allListScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAllPlanFragment(
                        filter = null
                    )
                )
            }
            countDownTimerScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCountDownTimerFragment()
                )
            }
            stopWatchScreen.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_stopWatchFragment
                )
            }
        }
    }
}