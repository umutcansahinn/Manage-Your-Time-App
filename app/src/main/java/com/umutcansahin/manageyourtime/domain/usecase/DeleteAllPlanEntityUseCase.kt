package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.RoomResponse
import kotlinx.coroutines.flow.Flow

interface DeleteAllPlanEntityUseCase {
    suspend operator fun invoke(): Flow<RoomResponse>
}