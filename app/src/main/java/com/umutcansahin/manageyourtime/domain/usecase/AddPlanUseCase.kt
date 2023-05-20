package com.umutcansahin.manageyourtime.domain.usecase

import android.content.Context
import com.umutcansahin.manageyourtime.common.Resource
import kotlinx.coroutines.flow.Flow

interface AddPlanUseCase {
    operator fun invoke(name: String?, time: String?, context: Context): Flow<Resource<String>>
}