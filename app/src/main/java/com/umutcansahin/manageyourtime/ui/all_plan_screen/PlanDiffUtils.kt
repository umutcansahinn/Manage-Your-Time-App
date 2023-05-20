package com.umutcansahin.manageyourtime.ui.all_plan_screen

import androidx.recyclerview.widget.DiffUtil
import com.umutcansahin.manageyourtime.data.local.PlanEntity

class PlanDiffUtils: DiffUtil.ItemCallback<PlanEntity>() {

    override fun areItemsTheSame(oldItem: PlanEntity, newItem: PlanEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlanEntity, newItem: PlanEntity): Boolean {
        return oldItem == newItem
    }
}