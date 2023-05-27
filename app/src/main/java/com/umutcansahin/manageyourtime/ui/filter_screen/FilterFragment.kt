package com.umutcansahin.manageyourtime.ui.filter_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umutcansahin.manageyourtime.common.filter.FavoriteType
import com.umutcansahin.manageyourtime.common.filter.Filter
import com.umutcansahin.manageyourtime.common.filter.SortedBy
import com.umutcansahin.manageyourtime.databinding.FragmentFilterBinding


class FilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val args: FilterFragmentArgs by navArgs()
    private var filter = Filter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoFromArgs()
    }

    private fun getInfoFromArgs() {
        filter = args.filter
        initView()
        setUi()
        setFilter()
    }

    private fun initView() {
        with(binding) {
            editTextStartTime.setText(filter.startTime)
            editTextEndTime.setText(filter.endTime)

            when (filter.favoriteType) {
                FavoriteType.ALL_ITEM -> {
                    checkBoxAllItem.isChecked = true
                    checkBoxIsFavoriteItem.isChecked = true
                    checkBoxIsNotFavoriteItem.isChecked = true
                }
                FavoriteType.IS_FAVORITE_ITEM -> {
                    checkBoxIsFavoriteItem.isChecked = true
                }
                FavoriteType.IS_NOT_FAVORITE_ITEM -> {
                    checkBoxIsNotFavoriteItem.isChecked = true
                }
            }

            when (filter.sortedBy) {
                SortedBy.DESC -> {
                    radioButtonDesc.isChecked = true
                }
                SortedBy.ASC -> {
                    radioButtonAsc.isChecked = true
                }
            }
        }
    }

    private fun setUi() {
        with(binding) {
            checkBoxAllItem.setOnClickListener {
                val isChecked = checkBoxAllItem.isChecked
                checkBoxIsFavoriteItem.isChecked = isChecked
                checkBoxIsNotFavoriteItem.isChecked = isChecked
            }
            checkBoxIsFavoriteItem.setOnClickListener {
                checkBoxAllItem.isChecked = checkBoxIsFavoriteItem.isChecked && checkBoxIsNotFavoriteItem.isChecked
            }
            checkBoxIsNotFavoriteItem.setOnClickListener {
                checkBoxAllItem.isChecked = checkBoxIsFavoriteItem.isChecked && checkBoxIsNotFavoriteItem.isChecked
            }
        }
    }

    private fun setFilter() {
        with(binding) {
            buttonCancel.setOnClickListener {
                findNavController().navigate(
                    FilterFragmentDirections.actionFilterFragmentToAllPlanFragment(
                        filter = filter
                    )
                )
            }
            buttonReset.setOnClickListener {
                filter = Filter()
                initView()
            }
            buttonApply.setOnClickListener {
                filter.startTime = editTextStartTime.text.toString()
                filter.endTime = editTextEndTime.text.toString()

                when {
                    checkBoxAllItem.isChecked -> {
                        filter.favoriteType = FavoriteType.ALL_ITEM
                    }
                    checkBoxIsFavoriteItem.isChecked -> {
                        filter.favoriteType = FavoriteType.IS_FAVORITE_ITEM
                    }
                    checkBoxIsNotFavoriteItem.isChecked -> {
                        filter.favoriteType = FavoriteType.IS_NOT_FAVORITE_ITEM
                    }
                    else-> filter.favoriteType = FavoriteType.ALL_ITEM
                }
                when {
                    radioButtonDesc.isChecked -> {
                        filter.sortedBy = SortedBy.DESC
                    }
                    radioButtonAsc.isChecked -> {
                        filter.sortedBy = SortedBy.ASC
                    }
                }
                findNavController().navigate(
                    FilterFragmentDirections.actionFilterFragmentToAllPlanFragment(
                        filter = filter
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}