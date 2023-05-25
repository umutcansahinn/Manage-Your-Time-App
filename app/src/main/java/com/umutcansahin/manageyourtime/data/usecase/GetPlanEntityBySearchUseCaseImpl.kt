package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityBySearchUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPlanEntityBySearchUseCaseImpl(
    private val planRepository: PlanRepository
) : GetPlanEntityBySearchUseCase {
    override fun invoke(search: String): Flow<Resource<List<PlanEntity>>> = flow {
        try {
            planRepository.getPlanEntityBySearch(search).collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}