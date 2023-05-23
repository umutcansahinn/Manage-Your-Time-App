package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import kotlinx.coroutines.flow.Flow

interface GetPlanEntityBySearchUseCase {
     operator fun invoke(search: String) : Flow<Resource<List<PlanEntity>>>
}