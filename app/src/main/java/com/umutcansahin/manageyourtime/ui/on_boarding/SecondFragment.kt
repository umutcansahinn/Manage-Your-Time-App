package com.umutcansahin.manageyourtime.ui.on_boarding

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
        val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.on_boarding2)
        val bitmap = drawable?.toBitmap()

        val newWith = bitmap?.width?.div(2)
        val newHeight = bitmap?.height?.div(2)

        val newBitmap = Bitmap.createScaledBitmap(bitmap!!,newWith!!,newHeight!!,true)
        binding.imageView.setImageBitmap(newBitmap)
    }
}