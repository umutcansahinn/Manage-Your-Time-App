package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.*
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.AddPlanUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddPlanUseCaseImpl(
    private val planRepository: PlanRepository
) : AddPlanUseCase {

    override fun invoke(
        name: String?,
        time: String?
    ): Flow<RoomResponse> = flow {
        emit(RoomResponse.Loading)
        when {
            name.isNullOrBlank() -> emit(RoomResponse.Error(ErrorType.NAME_IS_NULL_OR_BLANK_ERROR))
            name.isEmpty() -> emit(RoomResponse.Error(ErrorType.NAME_IS_EMPTY_ERROR))
            time.isNullOrBlank() -> emit(RoomResponse.Error(ErrorType.TIME_IS_NULL_OR_BLANK_ERROR))
            time.isEmpty() -> emit(RoomResponse.Error(ErrorType.TIME_IS_EMPTY_ERROR))
            time.toIntAndCheckIfEqualsZero() -> emit(RoomResponse.Error(ErrorType.TIME_IS_EQUALS_ZERO_ERROR))
            else -> {
                try {
                    emit(RoomResponse.Success)
                    planRepository.addPlan(
                        PlanEntity(
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