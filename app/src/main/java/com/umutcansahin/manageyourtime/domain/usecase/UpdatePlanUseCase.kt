package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.RoomResponse
import kotlinx.coroutines.flow.Flow

interface UpdatePlanUseCase {
    suspend operator fun invoke(name: String?, time: String?, id: Int): Flow<RoomResponse>
}