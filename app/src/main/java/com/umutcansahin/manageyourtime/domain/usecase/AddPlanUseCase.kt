package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.RoomResponse
import kotlinx.coroutines.flow.Flow

interface AddPlanUseCase {
    operator fun invoke(name: String?, time: String?): Flow<RoomResponse>
}