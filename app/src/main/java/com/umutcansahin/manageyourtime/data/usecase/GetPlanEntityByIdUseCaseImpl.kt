package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPlanEntityByIdUseCaseImpl(
    private val planRepository: PlanRepository
) : GetPlanEntityByIdUseCase {
    override fun invoke(entityId: Int): Flow<Resource<PlanEntity>> = flow {
        emit(Resource.Loading)
        try {
            planRepository.getPlanEntityById(entityId).collect {
                emit(Resource.Success(data = it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}