package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.ErrorType
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.DeletePlanUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeletePlanUseCaseImpl(
    private val planRepository: PlanRepository
) : DeletePlanUseCase {

    override suspend fun invoke(planEntity: PlanEntity): Flow<RoomResponse> = flow {
        try {
            planRepository.deletePlan(planEntity)
            emit(RoomResponse.Success)
        } catch (e: Exception) {
            emit(RoomResponse.Error(ErrorType.ROOM_DEFAULT_ERROR))
        }
    }
}