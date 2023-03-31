package com.example.domain.usecases.loginUsecases.floorUsecases

import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.repositories.BlocRepository
import com.example.domain.repositories.FloorRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFloorUseCase @Inject constructor(val floorRepository: FloorRepository) {

    operator fun invoke(name:String,number:Int, id:Int,bloc_id:Int) : Flow<Resource<MutableList<Floor>>> {
        return floorRepository.updateFloor(name,number,id,bloc_id)
    }

}