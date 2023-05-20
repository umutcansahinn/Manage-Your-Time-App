package com.umutcansahin.manageyourtime.ui.detail_plan

import com.umutcansahin.manageyourtime.data.local.PlanEntity

sealed interface DetailPlanState {
    object Loading: DetailPlanState
    data class Success(val entity: PlanEntity):DetailPlanState
    data class Error(val message: String?): DetailPlanState
}