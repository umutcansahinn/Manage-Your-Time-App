package com.umutcansahin.manageyourtime.ui.on_boarding

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.databinding.FragmentOnBoardingBinding


class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter

        if (onBoardingIsFinished()) {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToHomeFragment())
        }
    }

    private fun onBoardingIsFinished(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                getString(R.string.on_boarding),
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getBoolean(getString(R.string.finished), false)
    }
}