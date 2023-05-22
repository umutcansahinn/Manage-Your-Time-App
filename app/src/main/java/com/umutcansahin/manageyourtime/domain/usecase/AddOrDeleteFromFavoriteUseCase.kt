package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import kotlinx.coroutines.flow.Flow

interface AddOrDeleteFromFavoriteUseCase {
    suspend operator fun invoke(entity: PlanEntity): Flow<RoomResponse>
}