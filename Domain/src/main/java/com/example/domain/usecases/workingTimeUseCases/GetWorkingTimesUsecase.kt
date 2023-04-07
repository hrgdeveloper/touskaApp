package com.example.domain.usecases.workingTimeUseCases

import com.example.domain.models.WorkingTime
import com.example.domain.repositories.WorkingTimeRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkingTimesUsecase @Inject constructor(val workingTimeRepository: WorkingTimeRepository) {

    operator fun invoke() : Flow<Resource<MutableList<WorkingTime>>> {
        return workingTimeRepository.getWorkingTimes()
    }

}