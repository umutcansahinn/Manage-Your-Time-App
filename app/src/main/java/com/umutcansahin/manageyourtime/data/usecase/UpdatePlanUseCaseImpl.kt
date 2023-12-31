package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.ErrorType
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.common.extensions.convertToMillisecond
import com.umutcansahin.manageyourtime.common.extensions.toIntAndCheckIfEqualsZero
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.UpdatePlanUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePlanUseCaseImpl(
    private val planRepository: PlanRepository
) : UpdatePlanUseCase {
    override suspend fun invoke(
        name: String?,
        time: String?,
        id: Int
    ): Flow<RoomResponse> = flow {
        when {
            name == null -> emit(RoomResponse.Error(ErrorType.NAME_IS_NULL_ERROR))
            time == null -> emit(RoomResponse.Error(ErrorType.TIME_IS_NULL_ERROR))
            name.isBlank()-> emit(RoomResponse.Error(ErrorType.NAME_IS_BLANK_ERROR))
            time.isBlank()-> emit(RoomResponse.Error(ErrorType.TIME_IS_BLANK_ERROR))
            time.toIntAndCheckIfEqualsZero() -> emit(RoomResponse.Error(ErrorType.TIME_IS_EQUALS_ZERO_ERROR))
            else -> {
                try {
                    emit(RoomResponse.Success)
                    planRepository.updatePlan(
                        PlanEntity(
                            id = id,
                            name = name,
                            time = time.convertToMillisecond()
                        )
                    )
                } catch (e: Exception) {
                    emit(RoomResponse.Error(ErrorType.ROOM_DEFAULT_ERROR))
                }
            }
        }
    }
}