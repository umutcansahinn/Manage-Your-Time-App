package com.umutcansahin.manageyourtime.domain.usecase

import com.umutcansahin.manageyourtime.common.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteAllPlanEntityUseCase {
    suspend operator fun invoke(): Flow<Resource<String>>
}