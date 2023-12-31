package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.filter.FavoriteType
import com.umutcansahin.manageyourtime.common.filter.SortedBy
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import kotlinx.coroutines.flow.Flow

interface GetPlanEntityByFilterUseCase {
    operator fun invoke(
        sortedBy: SortedBy,
        favoriteType: FavoriteType,
        startTime: String,
        endTime: String
    ): Flow<Resource<List<PlanEntity>>>
}