package com.example.domain.usecases.loginUsecases.floorUsecases

import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.repositories.BlocRepository
import com.example.domain.repositories.FloorRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateFloorUsecase @Inject constructor(val floorRepository: FloorRepository) {

    operator fun invoke(name:String,bloc_id:Int,number:Int) : Flow<Resource<Floor>> {
        return floorRepository.addFloor(bloc_id,name,number)
    }

}