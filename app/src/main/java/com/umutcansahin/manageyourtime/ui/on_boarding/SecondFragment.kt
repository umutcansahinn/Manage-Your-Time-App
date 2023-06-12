package com.umutcansahin.manageyourtime.ui.on_boarding

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.databinding.FragmentSecondBinding

class SecondFragment: BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.textViewNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }
}