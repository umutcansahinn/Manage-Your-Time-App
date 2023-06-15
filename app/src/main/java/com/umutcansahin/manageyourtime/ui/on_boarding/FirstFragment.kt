package com.umutcansahin.manageyourtime.ui.on_boarding

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.viewpager2.widget.ViewPager2
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.extensions.makeSmallerImage
import com.umutcansahin.manageyourtime.databinding.FragmentFirstBinding


class FirstFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.textViewNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
        val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.on_boarding1)
        val bitmap = drawable?.toBitmap()
        binding.imageView.setImageBitmap(makeSmallerImage(bitmap!!,1_000))

    }

}