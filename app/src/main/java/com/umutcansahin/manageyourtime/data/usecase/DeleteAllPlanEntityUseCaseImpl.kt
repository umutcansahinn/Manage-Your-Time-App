package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.ErrorType
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.DeleteAllPlanEntityUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAllPlanEntityUseCaseImpl(
    private val planRepository: PlanRepository
) : DeleteAllPlanEntityUseCase {

    override suspend fun invoke(): Flow<RoomResponse> = flow {
        emit(RoomResponse.Loading)
        try {
            planRepository.deleteAllPlanEntity()
            emit(RoomResponse.Success)
        } catch (e: Exception) {
            emit(RoomResponse.Error(ErrorType.ROOM_DEFAULT_ERROR))
        }
    }
}