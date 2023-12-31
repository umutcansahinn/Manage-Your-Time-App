package com.umutcansahin.manageyourtime.ui.detail_plan_screen

import com.umutcansahin.manageyourtime.common.extensions.ZERO
import com.umutcansahin.manageyourtime.common.extensions.convertToMinuteAndSecond


data class DetailState(
    val textViewTime: Long = Long.ZERO,
    val isTimeNullOrBlank: Boolean = false,
    val isTimeFinish: Boolean = false
)