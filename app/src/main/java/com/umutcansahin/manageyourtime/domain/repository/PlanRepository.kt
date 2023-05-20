package com.umutcansahin.manageyourtime.domain.repository

import com.umutcansahin.manageyourtime.data.local.PlanEntity
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    suspend fun addPlan(planEntity: PlanEntity)
    suspend fun updatePlan(planEntity: PlanEntity)
    suspend fun deletePlan(planEntity: PlanEntity)
    suspend fun deleteAllPlanEntity()
    fun getAllPlanEntity(): Flow<List<PlanEntity>>
    fun getPlanEntityById(entityId: Int): Flow<PlanEntity>

}