package com.umutcansahin.manageyourtime.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlan(planEntity: PlanEntity)

    @Query("SELECT * FROM `plan` ORDER BY id DESC")
    fun getAllPlanEntity(): Flow<List<PlanEntity>>

    @Query("SELECT * FROM `plan` WHERE id=:entityId")
    fun getPlanEntityById(entityId: Int): Flow<PlanEntity>

    @Query("SELECT * FROM `plan` WHERE name LIKE '%' || :search || '%'")
    fun getPlanEntityBySearch(search: String): Flow<List<PlanEntity>>

    @Update
    suspend fun updatePlan(planEntity: PlanEntity)

    @Delete
    suspend fun deletePlan(planEntity: PlanEntity)

    @Query("DELETE FROM `plan`")
    suspend fun deleteAllPlanEntity()
}

