package com.umutcansahin.manageyourtime.domain.usecase

import android.content.Context
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import kotlinx.coroutines.flow.Flow

interface UpdatePlanUseCase {
    suspend operator fun invoke(name: String?, time: String?, id: Int, context: Context): Flow<Resource<String>>
}