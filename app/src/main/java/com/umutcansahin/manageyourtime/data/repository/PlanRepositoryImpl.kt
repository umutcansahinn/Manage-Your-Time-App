package com.umutcansahin.manageyourtime.data.repository

import com.umutcansahin.manageyourtime.data.local.PlanDao
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PlanRepositoryImpl(
    private val planDao: PlanDao,
    private val ioDispatcher: CoroutineDispatcher
) : PlanRepository {

    override suspend fun addPlan(planEntity: PlanEntity) {
        withContext(ioDispatcher) {
            planDao.addPlan(planEntity)
        }
    }

    override suspend fun updatePlan(planEntity: PlanEntity) {
        withContext(ioDispatcher) {
            planDao.updatePlan(planEntity)
        }
    }

    override suspend fun deletePlan(planEntity: PlanEntity) {
        withContext(ioDispatcher) {
            planDao.deletePlan(planEntity)
        }
    }

    override suspend fun deleteAllPlanEntity() {
        withContext(ioDispatcher) {
            planDao.deleteAllPlanEntity()
        }
    }

    override suspend fun getAllPlanEntity(): Flow<List<PlanEntity>> {
        return withContext(ioDispatcher) {
            planDao.getAllPlanEntity()
        }
    }

    override suspend fun getPlanEntityById(entityId: Int): Flow<PlanEntity> {
        return withContext(ioDispatcher) {
            planDao.getPlanEntityById(entityId)
        }
    }

    override suspend fun getPlanEntityBySearch(search: String): Flow<List<PlanEntity>> {
        return withContext(ioDispatcher) {
            planDao.getPlanEntityBySearch(search)
        }
    }

}