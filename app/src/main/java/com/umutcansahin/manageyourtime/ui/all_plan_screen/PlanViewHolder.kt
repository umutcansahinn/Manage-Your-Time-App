package com.umutcansahin.manageyourtime.ui.all_plan_screen

import androidx.recyclerview.widget.RecyclerView
import com.umutcansahin.manageyourtime.common.convertToMinuteAndSecond
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.databinding.PlanAdapterItemBinding

class PlanViewHolder(
    private val binding: PlanAdapterItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(entity: PlanEntity,itemClick:(Int)->Unit) {
        binding.apply {
            textViewTitle.text = entity.name
            textViewTime.text = entity.time.convertToMinuteAndSecond()
            root.setOnClickListener {
                itemClick.invoke(entity.id)
            }
        }
    }
}