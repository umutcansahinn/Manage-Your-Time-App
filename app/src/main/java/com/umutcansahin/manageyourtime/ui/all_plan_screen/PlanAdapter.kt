package com.umutcansahin.manageyourtime.ui.all_plan_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.databinding.PlanAdapterItemBinding

class PlanAdapter(
    private val itemClick: ((Int) -> Unit)
) : ListAdapter<PlanEntity, PlanViewHolder>(PlanDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        return PlanViewHolder(
            PlanAdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val entity = getItem(position)
        holder.bind(entity = entity, itemClick = itemClick)
    }
}