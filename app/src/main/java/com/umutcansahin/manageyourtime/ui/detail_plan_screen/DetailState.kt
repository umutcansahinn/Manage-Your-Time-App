package com.umutcansahin.manageyourtime.ui.detail_plan_screen

import com.umutcansahin.manageyourtime.common.ZERO
import com.umutcansahin.manageyourtime.common.convertToMinuteAndSecond


data class DetailState(
    val textViewTime: String = Long.ZERO.convertToMinuteAndSecond(),
    val isTimeNullOrBlank: Boolean = false,
    val isTimeFinish: Boolean = false
)