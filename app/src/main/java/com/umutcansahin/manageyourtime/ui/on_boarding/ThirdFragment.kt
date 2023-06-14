package com.umutcansahin.manageyourtime.ui.on_boarding

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment<FragmentThirdBinding>(FragmentThirdBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewFinish.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToHomeFragment())
            onBoardingIsFinished()
        }
        val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.on_boarding3)
        val bitmap = drawable?.toBitmap()

        val newWith = bitmap?.width?.div(2)
        val newHeight = bitmap?.height?.div(2)

        val newBitmap = Bitmap.createScaledBitmap(bitmap!!,newWith!!,newHeight!!,true)
        binding.imageView.setImageBitmap(newBitmap)
    }

    private fun onBoardingIsFinished() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                getString(R.string.on_boarding),
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putBoolean(getString(R.string.finished), true)
        editor.apply()
    }
}