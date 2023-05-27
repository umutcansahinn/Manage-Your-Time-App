package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.convertToMillisecond
import com.umutcansahin.manageyourtime.common.filter.FavoriteType
import com.umutcansahin.manageyourtime.common.filter.SortedBy
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityByFilterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetPlanEntityByFilterUseCaseImpl(
    private val planRepository: PlanRepository
) : GetPlanEntityByFilterUseCase {
    override fun invoke(
        sortedBy: SortedBy,
        favoriteType: FavoriteType,
        startTime: String,
        endTime: String
    ): Flow<Resource<List<PlanEntity>>> = flow {
        emit(Resource.Loading)
        try {
            planRepository.getAllPlanEntity().map { list ->
                val sortedList = when (sortedBy) {
                    SortedBy.DESC -> list.sortedByDescending { it.id }
                    SortedBy.ASC -> list.sortedBy { it.id }
                }
                val favoriteList = when (favoriteType) {
                    FavoriteType.IS_NOT_FAVORITE_ITEM -> sortedList.filter { !it.favorite }
                    FavoriteType.ALL_ITEM -> sortedList
                    FavoriteType.IS_FAVORITE_ITEM -> sortedList.filter { it.favorite }
                }
                favoriteList.filter {
                    it.time in startTime.convertToMillisecond()..endTime.convertToMillisecond()
                }
            }.collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}