package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.ErrorType
import com.umutcansahin.manageyourtime.common.RoomResponse
import com.umutcansahin.manageyourtime.common.extensions.convertToMillisecond
import com.umutcansahin.manageyourtime.common.extensions.toIntAndCheckIfEqualsZero
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.AddPlanUseCase

class AddPlanUseCaseImpl(
    private val planRepository: PlanRepository
) : AddPlanUseCase {

    override suspend fun invoke(name: String?, time: String?): RoomResponse {
        return when {
            name == null -> RoomResponse.Error(ErrorType.NAME_IS_NULL_ERROR)
            time == null -> RoomResponse.Error(ErrorType.TIME_IS_NULL_ERROR)
            name.isBlank() -> RoomResponse.Error(ErrorType.NAME_IS_BLANK_ERROR)
            time.isBlank() -> RoomResponse.Error(ErrorType.TIME_IS_BLANK_ERROR)
            time.toIntAndCheckIfEqualsZero() -> RoomResponse.Error(ErrorType.TIME_IS_EQUALS_ZERO_ERROR)
            else -> {
                try {
                    planRepository.addPlan(
                        PlanEntity(
                            name = name,
                            time = time.convertToMillisecond()
                        )
                    )
                    RoomResponse.Success
                } catch (e: Exception) {
                    RoomResponse.Error(ErrorType.ROOM_DEFAULT_ERROR)
                }
            }
        }
    }
}