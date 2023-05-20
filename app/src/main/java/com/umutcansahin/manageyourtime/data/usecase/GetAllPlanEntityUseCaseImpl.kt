package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.GetAllPlanEntityUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllPlanEntityUseCaseImpl(
    private val planRepository: PlanRepository
) : GetAllPlanEntityUseCase {
    override suspend fun invoke(): Flow<Resource<List<PlanEntity>>> = flow {
        try {
            emit(Resource.Loading)
            planRepository.getAllPlanEntity().collect {
                emit(Resource.Success(data = it))
            }
        }catch (e: Exception) {
            emit(Resource.Error(e.message ))
        }
    }
}