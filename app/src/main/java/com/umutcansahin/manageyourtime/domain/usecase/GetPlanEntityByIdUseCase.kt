package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import kotlinx.coroutines.flow.Flow

interface GetPlanEntityByIdUseCase {
    operator fun invoke(entityId: Int): Flow<Resource<PlanEntity>>
}