package com.umutcansahin.manageyourtime.data.usecase

import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.extensions.convertToMillisecond
import com.umutcansahin.manageyourtime.common.filter.FavoriteType
import com.umutcansahin.manageyourtime.common.filter.Filter
import com.umutcansahin.manageyourtime.common.filter.SortedBy
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.GetPlanEntityBySearchUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetPlanEntityBySearchUseCaseImpl(
    private val planRepository: PlanRepository,
    private val defaultDispatcher: CoroutineDispatcher
) : GetPlanEntityBySearchUseCase {
    override fun invoke(search: String, filter: Filter): Flow<Resource<List<PlanEntity>>> = flow {
        emit(Resource.Loading)
        try {
            planRepository.getPlanEntityBySearch(search).map { list ->
                val sortedList = when (filter.sortedBy) {
                    SortedBy.DESC -> list.sortedByDescending { it.id }
                    SortedBy.ASC -> list.sortedBy { it.id }
                }
                val favoriteList = when (filter.favoriteType) {
                    FavoriteType.IS_NOT_FAVORITE_ITEM -> sortedList.filter { !it.favorite }
                    FavoriteType.ALL_ITEM -> sortedList
                    FavoriteType.IS_FAVORITE_ITEM -> sortedList.filter { it.favorite }
                }
                favoriteList.filter {
                    it.time in filter.startTime.convertToMillisecond()..filter.endTime.convertToMillisecond()
                }
            }.collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }.flowOn(defaultDispatcher)
}