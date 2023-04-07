package com.example.domain.usecases.workingTimeUseCases

import com.example.domain.models.WorkingTime
import com.example.domain.repositories.WorkingTimeRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateWorkingTimeUseCase @Inject constructor(val workingTimeRepository: WorkingTimeRepository) {

    operator fun invoke(title:String,startTime:String,endTime:String) : Flow<Resource<WorkingTime>> {
        return workingTimeRepository.addWorkingTime(title,startTime,endTime)
    }

}