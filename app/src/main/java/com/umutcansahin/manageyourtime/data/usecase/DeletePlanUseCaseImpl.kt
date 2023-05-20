package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.DeletePlanUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeletePlanUseCaseImpl(
    private val planRepository: PlanRepository
) : DeletePlanUseCase {

    override suspend fun invoke(planEntity: PlanEntity): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading)
            planRepository.deletePlan(planEntity)
            emit(Resource.Success("Successful"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}