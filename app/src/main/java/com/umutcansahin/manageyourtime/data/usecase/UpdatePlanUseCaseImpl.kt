package com.umutcansahin.manageyourtime.data.usecase

import android.content.Context
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.common.Resource
import com.umutcansahin.manageyourtime.common.convertToMillisecond
import com.umutcansahin.manageyourtime.data.local.PlanEntity
import com.umutcansahin.manageyourtime.domain.repository.PlanRepository
import com.umutcansahin.manageyourtime.domain.usecase.UpdatePlanUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePlanUseCaseImpl(
    private val planRepository: PlanRepository
) : UpdatePlanUseCase {
    override suspend fun invoke(
        name: String?,
        time: String?,
        id: Int,
        context: Context
    ): Flow<Resource<String>> = flow {
        when {
            name.isNullOrBlank() || name.isEmpty() -> {
                emit(Resource.Error(context.getString(R.string.title_toast_message)))
            }
            time.isNullOrBlank() || time.isEmpty() -> {
                emit(Resource.Error(context.getString(R.string.time_toast_message)))
            }
            else -> {
                try {
                    emit(Resource.Loading)
                    emit(Resource.Success(context.getString(R.string.succesful)))
                    planRepository.updatePlan(
                        PlanEntity(
                            id = id,
                            name = name,
                            time = time.convertToMillisecond()
                        )
                    )
                } catch (e: Exception) {
                    emit(Resource.Error(e.message))
                }
            }
        }
    }
}