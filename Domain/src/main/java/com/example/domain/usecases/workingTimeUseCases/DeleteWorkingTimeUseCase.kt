package com.example.domain.usecases.workingTimeUseCases
import com.example.domain.models.WorkingTime
import com.example.domain.repositories.WorkingTimeRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteWorkingTimeUseCase @Inject constructor(val workingTimeRepository: WorkingTimeRepository) {

    operator fun invoke(id:Int) : Flow<Resource<MutableList<WorkingTime>>> {
        return workingTimeRepository.deleteWorkingTime(id)
    }

}