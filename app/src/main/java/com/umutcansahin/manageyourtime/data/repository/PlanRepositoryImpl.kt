package com.umutcansahin.manageyourtime.data.repository

import com.umutcansahin.manageyourtime.data.local.PlanDao
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow

class PlanRepositoryImpl(
    private val planDao: PlanDao
): PlanRepository  {

    override suspend fun addPlan(planEntity: PlanEntity) = planDao.addPlan(planEntity)

    override suspend fun updatePlan(planEntity: PlanEntity) = planDao.updatePlan(planEntity)

    override suspend fun deletePlan(planEntity: PlanEntity) = planDao.deletePlan(planEntity)

    override suspend fun deleteAllPlanEntity() = planDao.deleteAllPlanEntity()

    override fun getAllPlanEntity(): Flow<List<PlanEntity>> = planDao.getAllPlanEntity()

    override fun getPlanEntityById(entityId: Int): Flow<PlanEntity> = planDao.getPlanEntityById(entityId)
}