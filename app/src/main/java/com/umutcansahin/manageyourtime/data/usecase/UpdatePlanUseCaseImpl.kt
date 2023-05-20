package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.UpdatePlanUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePlanUseCaseImpl(
    private val planRepository: PlanRepository
) : UpdatePlanUseCase {

    override suspend fun invoke(planEntity: PlanEntity): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading)
            planRepository.updatePlan(planEntity)
            emit(Resource.Success("Successful"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}