package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.ErrorType
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.AddOrDeleteFromFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddOrDeleteFromFavoriteUseCaseImpl(
    private val planRepository: PlanRepository
) : AddOrDeleteFromFavoriteUseCase {
    override suspend fun invoke(entity: PlanEntity): Flow<RoomResponse> = flow {
        try {
            planRepository.updatePlan(entity)
            emit(RoomResponse.Success)
        }catch (e: Exception) {
            emit(RoomResponse.Error(ErrorType.ROOM_DEFAULT_ERROR))
        }
    }
}