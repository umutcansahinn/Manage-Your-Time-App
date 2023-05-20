package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.DeleteAllPlanEntityUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAllPlanEntityUseCaseImpl(
    private val planRepository: PlanRepository
) : DeleteAllPlanEntityUseCase {

    override suspend fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading)
            planRepository.deleteAllPlanEntity()
            emit(Resource.Success("Successful"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}