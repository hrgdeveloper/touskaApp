package com.example.domain.usecases.loginUsecases.floorUsecases
import com.example.domain.models.Floor
import com.example.domain.repositories.FloorRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFloorUseCase @Inject constructor(val floorRepository: FloorRepository) {

    operator fun invoke(id:Int,bloc_id: Int) : Flow<Resource<MutableList<Floor>>> {
        return floorRepository.deleteBloc(id,bloc_id)
    }

}